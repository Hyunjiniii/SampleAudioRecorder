package com.example.sampleaudiorecorder;

import android.content.ContentValues;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final private static String RECORDED_FILE = "/sdcard/recorded.mp4";
    MediaPlayer player;
    MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recordStartBtn = (Button) findViewById(R.id.recordStartBtn);
        recordStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (recorder != null) {
//                    recorder.stop();
//                    recorder.release();
//                    recorder = null;
//                }

                recorder = new MediaRecorder();

                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);  // 마이크로 입력
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  // MPEG4로 미디어 포맷설정
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);  // 디폴트인코더 사용
                recorder.setOutputFile(RECORDED_FILE);  // 결과물 파일 설정

                // 녹음을 위한 정보를 모두 설정한 뒤 녹음 시작
                try {
                    recorder.prepare();
                    recorder.start();
                } catch (Exception e) {
                    Log.e("SampleAudioRecorder", "Exeption : ", e);
                }
            }
        });

        Button recordStopBtn = (Button) findViewById(R.id.recordStopBtn);
        recordStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recorder == null)
                    return;

                // 녹음을 중지하기 위한 메소드
                recorder.stop();
                recorder.release();
                recorder = null;

//                ContentValues values = new ContentValues(10);
//
//                values.put(MediaStore.MediaColumns.TITLE, "Recorded");
//                values.put(MediaStore.Audio.Media.ALBUM, "Audio Album");
//                values.put(MediaStore.Audio.Media.ARTIST, "Mike");
//                values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Audio");
//                values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
//                values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
//                values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis()/1000);
//                values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4");  // 미디어파일의 포
//                values.put(MediaStore.Audio.Media.DATA, RECORDED_FILE);  // 녹음 파일의 이름을 지정
//
//                // ContentValues 객체에 넣은 정보들을 insert()메소드로 추가
//                // 미디어 앨범에서 음성파일에 대한 내용 제공자는 Uri 형식
//               Uri audioUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
//                if (audioUri == null) {
//                    Log.d("SampleAudioRecorder", "Audio insert failed.");
//                    return;
//                }
            }
        });

        Button playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null) {
                    player.stop();
                    player.release();
                    player = null;
                }
                try {
                    player = new MediaPlayer();

                    player.setDataSource(RECORDED_FILE);
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button pauseBtn = (Button) findViewById(R.id.pauseBtn);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player == null)
                    return;

                player.pause();
                player.release();
                player = null;
            }
        });

    }

    // 액티비티 중지 시 리소스 해제
    protected void onPause() {
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        super.onPause();
    }

    // 액티비티 재시작 시 MediaRecorder 객체 생성 뒤 필요한 정보 설정
    protected void onResume() {
        super.onResume();
        recorder = new MediaRecorder();
    }
}
