package com.example.q.animequizz;

import android.os.AsyncTask;
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

public class ImageSearch extends AsyncTask<String, String, JSONObject[]> {

    SoloAnswerActivity act;
    DuoAnswerActivity actDuo;

    int mode=0;

    public ImageSearch(SoloAnswerActivity act)
    {
        this.act=act;
        mode=0;
    }

    public ImageSearch(DuoAnswerActivity act)
    {
        this.actDuo=act;
        mode = 1;
    }
    protected JSONObject[] doInBackground(String... strings)  {




        HttpURLConnection conn;
        URL url;

        String[] urls = GetUrls(strings[0], strings[1]);
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
                    if(i%2==0)
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
                            return (JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]);
                        }


                        if(json.getJSONArray("result").length()>2)
                        {
                            String title2 = Lower(Strip(json.getJSONArray("result").getJSONObject(1).getString("title")," .?!,"));
                            if(title2.equals(proposition))
                            {
                                jsonList=new ArrayList<>();
                                jsonList.add(json);
                                return (JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]);
                            }
                        }
                        if(json.getJSONArray("result").length()>3)
                        {
                         String title3 = Lower(Strip(json.getJSONArray("result").getJSONObject(2).getString("title")," .?!,"));
                         if(title3.equals(proposition))
                          {
                                    jsonList=new ArrayList<>();
                                    jsonList.add(json);
                                    return (JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]);
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

        return (JSONObject[])jsonList.toArray(new JSONObject[jsonList.size()]);

    }


    @Override
    protected void onPostExecute(JSONObject[] jsons) {

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
        try
        {
            Log.i("AnimeQuizz", "AnimeStuff:Elected object: " + selectedObject.toString());
            url_image=selectedObject.getString("image_url");
        }
        catch(Exception e)
        {
            url_image="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/emojidex/112/shrug_1f937.png";
        }


        BitmapDownloader bd;
        if(mode==0)
        {
            bd = new BitmapDownloader(act);
            bd.execute(url_image);
        }
        else if(mode==1)
        {
            bd = new BitmapDownloader(actDuo);
            bd.execute(url_image);
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

        /*Log.i("AnimeQuizz", "AnimeStuff: All words: ");
        for(String s:words)
        {
            Log.i("AnimeQuizz", "AnimeStuff: " + s);
        }*/


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


}
