package com.mapplinks.durofyreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private  String mFileContents;
    private  ArticleList mArticles;
    private Article article;
    private ArticleAdapter mAdapter;
    private ParseArticles parseArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.durofy.com/feed/");


/*
        for (int i = 0; i < 30; ++i) {
            Article article1 = new Article();
            article1.setTitle("Hello! this is your post which is supposed to be huge and so it is. The is post number " + (i + 1));
            article1.setAuthor("Author Name | DD:MM:YYYY");
            mArticles.add(article1);
        }*/

    }

    private class ArticleAdapter extends ArrayAdapter<Article> {
        ArticleAdapter(ArrayList<Article> articles) {
            super(MainActivity.this, R.layout.article_list_view, R.id.title, articles);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            Article article = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
            TextView summaryTextView = (TextView) convertView.findViewById(R.id.summary);
            TextView categoryTextView = (TextView) convertView.findViewById(R.id.category);
            ImageView image = (ImageView) convertView.findViewById(R.id.image);

            titleTextView.setText(article.getTitle());
            summaryTextView.setText(article.getAuthor() + " | " + article.getTimeStamp());
            categoryTextView.setText(article.getCategory());
            Picasso.with(MainActivity.this)
                    .load(article.getImageUrl())
                    .error(R.drawable.default_article_thumbnail)
                    .into(image);
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);
            if (mFileContents == null) {
                Log.d("DownloadData", "Error Downloading");
            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, "XML Downloaded!", Toast.LENGTH_SHORT).show();
            populateListView();
            Log.d("onPostExecute", "Downloaded TT.TT");
            //  xmlTextView.setText(mFileContents);
        }

        private String downloadXMLFile(String urlPath) {
            StringBuilder tempBuffer = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData", " Response Code: " + response);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];

                while (true) {
                    charRead = isr.read(inputBuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
                }
                return tempBuffer.toString();

            } catch (IOException e) {
                Log.d("DownloadData", "Error! TT.TT" + e.getMessage());
            } catch (SecurityException s) {
                Log.d("DownloadData", "Security Exception. Needs permission? " + s.getMessage());
            }
            return null;
        }
    }

    public void populateListView(){

        parseArticles = new ParseArticles(mFileContents);
        parseArticles.process();
        ListView listView = (ListView) findViewById(R.id.article_list);
        mArticles = ArticleList.getInstance();
        mArticles=parseArticles.getArticleList();
        mAdapter = new ArticleAdapter(mArticles);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > previousFirstItem) {
                } else if (firstVisibleItem < previousFirstItem) {
                    getSupportActionBar().show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                article=mArticles.get(position);
                Toast.makeText(MainActivity.this, "URL: "+ article.getLink(), Toast.LENGTH_SHORT).show();
                Intent pass =new Intent(MainActivity.this,WebActivity.class);
                pass.putExtra("Description", article.getDescription());
                startActivity(pass);
            }
        });

    }

}
