package me.tabak.mediastoreframetest;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private ImageAdapter mAdapter;
    private ListView mListView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ImageAdapter(this);

        mButton = (Button) findViewById(R.id.select_video_button);
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent, 0);
                mButton.setEnabled(false);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mButton.setEnabled(true);
            if (!"media".equals(data.getData().getHost())) {
                Toast.makeText(this,
                        "Please choose a video in your media store.", Toast.LENGTH_SHORT).show();
                return;
            }
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this, data.getData());
            String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            Long millis = Long.parseLong(duration);
            mAdapter.clear();
            for (int i = 0; i < millis * 1000; i += 250 * 1000) {
                mAdapter.add(data.getData().buildUpon()
                        .appendQueryParameter("t", String.valueOf(i)).build());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
