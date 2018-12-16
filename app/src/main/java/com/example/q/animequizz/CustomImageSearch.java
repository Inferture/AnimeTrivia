package com.example.q.animequizz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomImageSearch extends AsyncTask<Integer, String, Bitmap> {
    /*Look for an image asynchronously and loads this image into an answer activity, for the custom mode (using the database questions)*/


    SoloAnswerActivity act;
    DuoAnswerActivity actDuo;
    public QuestionDbHelper qdh;
    int mode=0;

    int resmalid=-1;
    int restype=-1;

    public CustomImageSearch(SoloAnswerActivity act)
    {
        this.act=act;
        mode=0;
    }

    public CustomImageSearch(DuoAnswerActivity act)
    {
        this.actDuo=act;
        mode = 1;
    }
    protected Bitmap doInBackground(Integer... ids)  {

        int id=ids[0];

        final SQLiteDatabase db = qdh.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION,
                AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER,
                AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER1,
                AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2,
                AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3,
                AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL,
                AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT,
                AnimeContract.QuestionEntry.COLUMN_NAME_TYPE,
                AnimeContract.QuestionEntry.COLUMN_NAME_MALID
        };
        String selection = BaseColumns._ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        String sortOrder =
                AnimeContract.QuestionEntry._ID + " ASC";


        Log.i("AnimeQuizz", "AnimeStuff: prepared to look into it");

        Cursor cursor=null;
        try
        {
            cursor = db.query(
                    AnimeContract.QuestionEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );
            Log.i("AnimeQuizz", "AnimeStuff: made a query");
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: error when making a query :" + e.toString());
        }


        cursor.moveToNext();

        String question = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION));
        String rightanswer = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER));
        String falseanswer1 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER1));
        String falseanswer2 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2));
        String falseanswer3 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3));
        String imageurl = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL));
        String subject = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_TYPE));
        int malid = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_MALID));

        Bitmap bm=null;



        if(imageurl != null && !imageurl.equals("") )
        {
            bm=GetImage(imageurl);
        }
        if(bm!=null)
        {
            if(malid >0 && type >=1 && type<=3)
            {
                resmalid=malid;
                restype=type;
            }
            return bm;
        }


        HttpURLConnection conn;
        URL url;



        if(malid >0 && type >=1 && type<=3)
        {
            String stringtype="";

            if(type==1) {
                Log.i("AnimeQuizz", "AnimeStuff:anime: Let's check animes");
                stringtype="anime";
            }
            else if(type==2) {
                Log.i("AnimeQuizz", "AnimeStuff:anime: Let's check mangas");
                stringtype="manga";
            }
            else {
                Log.i("AnimeQuizz", "AnimeStuff:anime: Let's check characters");
                stringtype="character";
            }
            try
            {
                url = new URL("https://api.jikan.moe/v3/"+stringtype+"/" + malid);
                conn = (HttpURLConnection) url.openConnection();
                String jsonAnswer = "";
                try
                {

                    publishProgress("trying to read stream");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    jsonAnswer = readStream(in);

                    try
                    {
                        JSONObject json = new JSONObject(jsonAnswer);
                        imageurl = json.getString("image_url");

                    }
                    catch(Exception e)
                    {
                        Log.i("AnimeQuizz", "AnimeStuff:error when checking subject: "+e.toString());
                    }

                }
                catch (Exception e)
                {
                    Log.i("AnimeQuizz", "AnimeStuff:error when reading stream: "+e.toString());
                }

            }
            catch (Exception e)
            {
                Log.i("AnimeQuizz", "AnimeStuff:error when connecting:" +e.toString());
            }
            bm=GetImage(imageurl);
            if(bm!=null)
            {
                resmalid=malid;
                restype=type;
                return bm;
            }

        }






        String[] urls = new String[]{};

        if(subject==null || subject.equals(""))
        {
            urls=GetUrls(question, rightanswer);
        }
        else
        {
            String l;

            if(type==3)
            {
                restype=type;
                l=("https://api.jikan.moe/search/character?q=" + subject + "&limit=2");
            }
            else if(type==2)
            {
                restype=type;
                l=("https://api.jikan.moe/search/manga?q=" + subject + "&limit=2");
            }
            else
            {
                restype=1;
                l=("https://api.jikan.moe/search/anime?q=" + subject + "&limit=2");
            }
            urls=new String[]{l};
        }
        List<JSONObject> jsonList = new ArrayList<>();


        for(int i=0;i<urls.length;i++)
        {
            if(!isCancelled())
            {


                try
                {
                    url = new URL(urls[i]);
                    conn = (HttpURLConnection)url.openConnection();
                    String jsonAnswer="";
                    try
                    {
                        if(i%2==0 && type != 3)
                        {
                            publishProgress("trying to read stream");
                            InputStream in = new BufferedInputStream(conn.getInputStream());
                            jsonAnswer = readStream(in);
                            publishProgress("already read stream");


                            try
                            {
                                JSONObject json = new JSONObject(jsonAnswer);
                                String proposition=Strip(urls[i].toString().split("q=")[1].toLowerCase()," .?!");
                                proposition=Lower(Strip(proposition.split("&")[0]," .?!,"));
                                Log.i("AnimeQuizz", "AnimeStuff:anime: " + json.getJSONArray("result").getJSONObject(0).toString());
                                String title = Lower(Strip(json.getJSONArray("result").getJSONObject(0).getString("title")," .?!,"));




                                Log.i("AnimeQuizz", "AnimeStuff:Proposition: " + proposition + "/title:" + title);

                                if(title.equals(proposition))
                                {
                                    jsonList=new ArrayList<>();
                                    jsonList.add(json);
                                    bm=SetImage( (JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]));
                                    if(bm!=null)
                                    {

                                        try
                                        {
                                            resmalid= json.getJSONArray("result").getJSONObject(0).getInt("mal_id");
                                            restype=1;
                                        }
                                        catch(Exception e)
                                        {

                                        }


                                        return bm;
                                    }
                                }


                                if(json.getJSONArray("result").length()>2)
                                {
                                    String title2 = Lower(Strip(json.getJSONArray("result").getJSONObject(1).getString("title")," .?!,"));
                                    if(title2.equals(proposition))
                                    {
                                        jsonList=new ArrayList<>();
                                        jsonList.add(json);
                                        bm=SetImage( (JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]));
                                        if(bm!=null)
                                        {
                                            try
                                            {
                                                resmalid= json.getJSONArray("result").getJSONObject(1).getInt("mal_id");
                                                restype=1;
                                            }
                                            catch(Exception e)
                                            {

                                            }
                                            return bm;
                                        }
                                    }
                                }
                                if(json.getJSONArray("result").length()>3)
                                {
                                    String title3 = Lower(Strip(json.getJSONArray("result").getJSONObject(2).getString("title")," .?!,"));
                                    if(title3.equals(proposition))
                                    {
                                        jsonList=new ArrayList<>();
                                        jsonList.add(json);
                                        bm=SetImage( (JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]));
                                        if(bm!=null)
                                        {
                                            try
                                            {
                                                resmalid= json.getJSONArray("result").getJSONObject(2).getInt("mal_id");
                                                restype=1;
                                            }
                                            catch(Exception e)
                                            {

                                            }
                                            return bm;
                                        }
                                    }
                                }


                                jsonList.add(json);
                            }
                            catch (Exception e)
                            {
                                Log.i("AnimeQuizz", "AnimeStuff:ImageSearch Nothing to see here: " + e.toString());
                            }


                        }
                        else
                        {

                            Log.i("AnimeQuizz", "AnimeStuff: Trying to get the character");

                            publishProgress("trying to read stream");
                            InputStream in = new BufferedInputStream(conn.getInputStream());
                            jsonAnswer = readStream(in);
                            publishProgress("already read stream");

                            Log.i("AnimeQuizz", "AnimeStuff: Read character stream: " + jsonAnswer);
                            JSONObject jsonCharacter = new JSONObject(jsonAnswer);
                            JSONObject character = jsonCharacter.getJSONArray("result").getJSONObject(0);
                            Log.i("AnimeQuizz", "AnimeStuff: Got the character");


                            URL newURL;
                            if(type==3)
                            {
                                String url_image="";
                                try
                                {
                                    Log.i("AnimeQuizz", "AnimeStuff:Elected object: " + character.toString());
                                    url_image=character.getString("image_url");
                                }
                                catch(Exception e)
                                {

                                }

                                bm = GetImage(url_image);

                                if(bm != null)
                                {
                                    try
                                    {
                                        restype=3;
                                        resmalid=character.getInt("mal_id");
                                    }
                                    catch(Exception e)
                                    {

                                    }
                                    return bm;

                                }

                            }
                            if(character.getJSONArray("anime").length()>0)
                            {
                                JSONObject anime = character.getJSONArray("anime").getJSONObject(0);
                                String animeTitle = anime.getString("title");
                                Log.i("AnimeQuizz", "AnimeStuff: Got the title " + animeTitle);
                                newURL = new URL("https://api.jikan.moe/search/anime?q=" + animeTitle);
                                Log.i("AnimeQuizz", "AnimeStuff:" + newURL.toString());
                            }
                            else
                            {
                                JSONObject anime = character.getJSONArray("manga").getJSONObject(0);
                                String mangaTitle = anime.getString("title");
                                Log.i("AnimeQuizz", "AnimeStuff: Got the title");
                                newURL = new URL("https://api.jikan.moe/search/anime?q=" + mangaTitle);
                                Log.i("AnimeQuizz", "AnimeStuff: Got the title " + mangaTitle);
                            }


                            Log.i("AnimeQuizz", "AnimeStuff: Got the title");
                            HttpURLConnection conn2=(HttpURLConnection)newURL.openConnection();

                            publishProgress("trying to read stream");
                            in = new BufferedInputStream(conn2.getInputStream());
                            jsonAnswer=readStream(in);
                            publishProgress("already read stream");


                            try
                            {
                                JSONObject json = new JSONObject(jsonAnswer);

                                jsonList.add(json);
                            }
                            catch (Exception e)
                            {
                                Log.i("AnimeQuizz", "AnimeStuff:ImageSearch Nothing to see here: " + e.toString());
                            }


                        }



                    }
                    catch(Exception e)
                    {
                        Log.i("AnimeQuizz", "AnimeStuff:ImageSearch Mistake happened on getting values: " +i+" " + e.toString());
                    }


                    //

                    Log.i("AnimeQuizz", "AnimeStuff:ImageSearch " + urls[i].toString() + " "+jsonAnswer);
                    //
                    //return new JSONObject(jsonAnswer);


                }
                catch(Exception e)
                {
                    Log.i("AnimeQuizz", "AnimeStuff:ImageSearch Mistake happened on background stuff: " + e.toString());
                }
            }
        }

        bm=SetImage((JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]));

        return bm;

    }


    protected Bitmap SetImageCharacter(JSONObject json){
        String url_image="";
        try
        {
            Log.i("AnimeQuizz", "AnimeStuff:Elected object: " + json.toString());
            url_image=json.getString("image_url");
        }
        catch(Exception e)
        {
            url_image="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/emojidex/112/shrug_1f937.png";
        }

        Bitmap bm = GetImage(url_image);
        return bm;
    }

    protected Bitmap SetImage(JSONObject[] jsons) {

        JSONObject selectedObject = null;
        int fans = 0;


        for(int i=0;i<jsons.length;i++)
        {
            try
            {
                JSONObject result = jsons[i].getJSONArray("result").getJSONObject(0);
                int members = result.getInt("members");
                String title = result.getString("title");
                Log.i("AnimeQuizz", "AnimeStuff: Titre:" + title + " Membres: " + members);
                if(members>fans)
                {
                    selectedObject=result;
                    fans=members;
                }
            }
            catch(Exception e)
            {
                Log.i("AnimeQuizz", "AnimeStuff:ImageSearch Mistake happened on post execution: " + e.toString());
            }



        }


        String url_image="";
        String str_type="";
        try
        {
            Log.i("AnimeQuizz", "AnimeStuff:Elected object: " + selectedObject.toString());
            url_image=selectedObject.getString("image_url");
            resmalid=selectedObject.getInt("mal_id");
            str_type=selectedObject.getString("type");

        }
        catch(Exception e)
        {
            url_image="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/emojidex/112/shrug_1f937.png";
        }


        int typeid=-1;
        if(str_type.equals("TV") || str_type.equals("OVA") ||str_type.equals("ONA") ||str_type.equals("Special") ||str_type.equals("Movie") ||str_type.equals("Music"))
        {
            restype=1;
        }
        else if(str_type.equals("Manga") || str_type.equals("Novel") ||str_type.equals("One-shot") ||str_type.equals("Doujinshi") ||str_type.equals("Manhua") ||str_type.equals("OEL")||str_type.equals("Manhwa"))
        {
            restype=2;
        }


        Bitmap bm = GetImage(url_image);
        return bm;





    }

    @Override
    protected void onPostExecute(Bitmap bm) {
        super.onPostExecute(bm);

        Log.i("AnimeQuizz", "AnimeStuff:Elected object: TYPE: " +restype + " MAL ID: " +resmalid);

        if(restype>0 && resmalid>0)
        {
            if(mode==0)
            {
                act.LoadImage(bm,restype,resmalid);
            }
            else if(mode==1)
            {
                actDuo.LoadImage(bm,restype,resmalid);
            }
        }
        else
        {
            if(mode==0)
            {
                act.LoadImage(bm,restype,resmalid);
            }
            else if(mode==1)
            {
                actDuo.LoadImage(bm,restype,resmalid);
            }
        }

    }

    String readStream(InputStream stream) throws IOException
    {


        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


        for(String line = reader.readLine();line != null ;line=reader.readLine())
        {
            sb = sb.append(line);
        }

        String s = sb.toString();

        return s;

    }

    String[] GetUrls(String q, String a)
    {


        List<String> l = new ArrayList<>();

        if(q==null)
        {
            return null;
        }

        String[] qsq = q.split("\"");


        String nextSearch="";

        for (int i=0;2*i+1<qsq.length;i++)
        {
            nextSearch=qsq[2*i+1];
            l.add("https://api.jikan.moe/search/anime?q=" + nextSearch + "&limit=2");
            l.add("https://api.jikan.moe/search/character?q=" + nextSearch + "&limit=2");
            if(q.contains("anime "+ "\"" + nextSearch + "\"") || q.contains("in "+ "\"" + nextSearch + "\"") || q.contains("anime, "+ "\"" + nextSearch + "\"") || q.contains("In "+ "\"" + nextSearch + "\"") || q.contains("film "+ "\"" + nextSearch + "\"")) {
                List<String> l2 = new ArrayList<>();
                l2.add("https://api.jikan.moe/search/anime?q=" + nextSearch + "&limit=2");
                return (String[]) l.toArray(new String[l2.size()]);

            }

        }


        nextSearch="";




        String s0 = q.replace("\"","");

        String[] words = s0.split(" ");

        String[] notCounted = new String[]{"The","In","What","Who","How", "Which"};


        Log.i("AnimeQuizz", "AnimeStuff: Filtering possible titles in the question: " + q);
        for (int i=0;i<words.length;i++)
        {
            Log.i("AnimeQuizz", "AnimeStuff:" +words[i]);
            if(Character.isUpperCase(words[i].charAt(0)) && !(i==0 && (Contains(notCounted,words[i]))))
            {
                Log.i("AnimeQuizz", "AnimeStuff: selected");
                nextSearch+= " " + words[i];
            }
            else
            {
                Log.i("AnimeQuizz", "AnimeStuff: not selected");
                if(!nextSearch.equals(""))
                {
                    nextSearch=Strip(nextSearch," ,?!.");
                    l.add("https://api.jikan.moe/search/anime?q=" + nextSearch + "&limit=2");
                    l.add("https://api.jikan.moe/search/character?q=" + nextSearch+ "&limit=2");
                    nextSearch="";
                }
            }

        }
        if(!nextSearch.equals(""))
        {
            nextSearch=Strip(nextSearch," ,?!.");
            l.add("https://api.jikan.moe/search/anime?q=" + nextSearch);
            l.add("https://api.jikan.moe/search/character?q=" + nextSearch);
            nextSearch="";
        }
        if(!Lower(a).equals("true") && !Lower(a).equals("false") )
        {
            l.add("https://api.jikan.moe/search/anime?q=" + a);
            l.add("https://api.jikan.moe/search/character?q=" + a);
        }


        Log.i("AnimeQuizz", "AnimeStuff: Found "+ l.size() + " possible titles:");
        for(String s:l)
        {
            Log.i("AnimeQuizz", "AnimeStuff:ImageSearch: " + s);
        }
        return (String[])l.toArray(new String[l.size()]);


    }








    <T> Boolean Contains(T[] tab, T value)
    {
        for(T v:tab)
        {

            if(value.equals(v))
            {
                return true;
            }
        }
        return false;
    }

    public String Strip(String a, String b)
    {
        int i=0;
        while(i<a.length() && b.contains(a.charAt(i)+""))
        {
            i++;
        }
        int j=a.length()-1;
        while(j>=0 && b.contains(a.charAt(j)+""))
        {
            j--;
        }
        if(j<i)
        {
            return "";
        }
        return a.substring(i,j+1);
    }


    public String Lower(String a)
    {
        String b=a.toLowerCase();
        b=b.replace('é', 'e');
        b=b.replace('è', 'e');
        b=b.replace('ë', 'e');
        b=b.replace('ê', 'e');
        b=b.replace('à', 'a');
        b=b.replace('ô', 'o');
        b=b.replace('ù', 'u');
        b=b.replace('û', 'u');
        b=b.replace('ÿ', 'y');
        return b;
    }


    public Bitmap GetImage(String urlmedia)
    {
        Log.i("AnimeQuizz", "AnimeStuff:anime: Trying to get image at url: " +urlmedia);
        URL url=null;
        HttpURLConnection conn=null;
        Bitmap bm =null;

        try
        {
            publishProgress("Trying to connect");

            url=new URL(urlmedia);

            conn = (HttpURLConnection)url.openConnection();

            InputStream stream = null;
            try
            {
                InputStream is = conn.getInputStream();
                stream = new BufferedInputStream(is);
            }
            catch (Exception e)
            {
                Log.i("Anime", "Pomme1: Error when getting stream "+ e.toString());
            }
            //InputStream stream = new BufferedInputStream(conn.getInputStream());

            publishProgress("Trying decode stream");
            Log.i("Anime", "Pomme1: Trying to decode stream "+urlmedia);
            bm=BitmapFactory.decodeStream(stream);

            stream.close();
            Log.i("Anime", "Pomme1: Decoded stream "+urlmedia);
            publishProgress("Decoded stream");

        }
        catch(Exception e)
        {
            publishProgress(e.toString());
            Log.i("Anime", "Pomme1: when dling bitmap "+e.toString());
        }


        if(bm==null)
        {
            Log.i("AnimeQuizz", "AnimeStuff:anime: Null result at: " +urlmedia);
        }
        else
        {
            Log.i("AnimeQuizz", "AnimeStuff:anime: result at: " +urlmedia +"not null");
        }

        return bm;
    }


}
