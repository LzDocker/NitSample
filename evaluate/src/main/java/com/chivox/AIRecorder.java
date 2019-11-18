package com.chivox;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * FIXME (441000, 1, 16) is the only format that is guaranteed to work on all
 * devices
 *
 * @author shun.zhang
 */
public class AIRecorder {

    private static String TAG = "AIRecorder";

    private static int CHANNELS = 1;
    private static int BITS = 16;
    private static int FREQUENCY = 16000; // sample rate
    private static int INTERVAL = 100; // callback interval

    private String latestPath = null; // latest wave file path

    private volatile boolean running = false;

    private ExecutorService workerThread;
    private Future<?> future = null;

    public static interface Callback {
        public void onStarted();

        public void onData(byte[] data, int size);

        public void onStopped();
//         void onCancle();
    }

    public AIRecorder() {
        workerThread = Executors.newSingleThreadScheduledExecutor();
    }

    public boolean isRunning() {
        return running;
    }

    public int start(final String path, final Callback callback) {

        stop();

        printLog(Thread.currentThread().getName() + "starting");

        running = true;

        future = workerThread.submit(new Runnable() {

            @Override
            public void run() {
//                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_LOWEST);
                RandomAccessFile file = null;
                AudioRecord recorder = null;
                try {
                    if (path != null) {
                        file = fopen(path);
                    }

                    /*
                    int bufferSize = 320000; // 10s is enough
                    int minBufferSize = AudioRecord.getMinBufferSize(FREQUENCY, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);

                    if (minBufferSize > bufferSize) {
                        bufferSize = minBufferSize;
                    }
                    */
                    printLog("#recorder new AudioRecord() 0");
                    recorder = new AudioRecord(AudioSource.DEFAULT, FREQUENCY, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, 320000); // 10s is enough
                    printLog("#recorder new AudioRecord() 1");

                    printLog("#recorder.startRecording() 0");
                    recorder.startRecording();
                    printLog("#recorder.startRecording() 1");

                    printLog(Thread.currentThread().getName() + "started");
                    callback.onStarted();


                    /* TODO started callback */

                    /*
                     * discard the beginning 100ms for fixing the transient
                     * noise bug shun.zhang, 2013-07-08
                     */
                    byte buffer[] = new byte[CHANNELS * FREQUENCY * BITS * INTERVAL / 1000 / 8];

                    int discardBytes = CHANNELS * FREQUENCY * BITS * 100 / 1000 / 8;
                    while (discardBytes > 0) {
                        int requestBytes = buffer.length < discardBytes ? buffer.length : discardBytes;
                        int readBytes = recorder.read(buffer, 0, requestBytes);
                        if (readBytes > 0) {
                            discardBytes -= readBytes;
                            printLog("discard: " + readBytes);
                        } else {
                            break;
                        }
                    }

                    while (running) {

                        printLog(Thread.currentThread().getName() + "#recorder.getRecordingState() 0");
                        if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                            break;
                        }
                        printLog(Thread.currentThread().getName() + "#recorder.getRecordingState() 1");

                        printLog(Thread.currentThread().getName() + "#recorder.read() 0");
                        int size = recorder.read(buffer, 0, buffer.length);
//                        int nowVolume = calculateVolume(buffer, 16);
//                        Log.e(TAG, "nowVolume:" + nowVolume);
                        printLog("#recorder.read() 1 - " + size);
                        if (size > 0) {
                            if (callback != null) {
                                printLog("#recorder callback.run() 0");
                                callback.onData(buffer, size);
                                printLog("#recorder callback.run() 1");
                            }
                            if (file != null) {
                                printLog("#recorder fwrite() 0");
                                fwrite(file, buffer, 0, size);
                                printLog("#recorder fwrite() 1");
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    callback.onStopped(); // invoke onStopped before recorder.stop()
                    running = false;
                    if (recorder != null) {
                        if (recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                            printLog("#recorder.stop() 0");
                            recorder.stop(); // FIXME elapse 400ms
                            printLog("#recorder.stop() 1");
                        }
                        recorder.release();
                    }

                    printLog("record stoped");

                    if (file != null) {
                        try {
                            fclose(file);
                            latestPath = path;
                        } catch (IOException e) {
                            // ignore
                        }
                    }
                }
            }
        });

        return 0;
    }

    public static int calculateVolume(byte[] var0, int var1) {
        int[] var3 = null;
        int var4 = var0.length;
        int var2;
        if (var1 == 8) {
            var3 = new int[var4];
            for (var2 = 0; var2 < var4; ++var2) {
                var3[var2] = var0[var2];
            }
        } else if (var1 == 16) {
            var3 = new int[var4 / 2];
            for (var2 = 0; var2 < var4 / 2; ++var2) {
                byte var5 = var0[var2 * 2];
                byte var6 = var0[var2 * 2 + 1];
                int var13;
                if (var5 < 0) {
                    var13 = var5 + 256;
                } else {
                    var13 = var5;
                }
                short var7 = (short) (var13 + 0);
                if (var6 < 0) {
                    var13 = var6 + 256;
                } else {
                    var13 = var6;
                }
                var3[var2] = (short) (var7 + (var13 << 8));
            }
        }

        int[] var8 = var3;
        if (var3 != null && var3.length != 0) {
            float var10 = 0.0F;
            for (int var11 = 0; var11 < var8.length; ++var11) {
                var10 += (float) (var8[var11] * var8[var11]);
            }
            var10 /= (float) var8.length;
            float var12 = 0.0F;
            for (var4 = 0; var4 < var8.length; ++var4) {
                var12 += (float) var8[var4];
            }
            var12 /= (float) var8.length;
            var4 = (int) (Math.pow(2.0D, (double) (var1 - 1)) - 1.0D);
            double var14 = Math.sqrt((double) (var10 - var12 * var12));
            int var9;
            if ((var9 = (int) (10.0D * Math.log10(var14 * 10.0D * Math.sqrt(2.0D) / (double) var4 + 1.0D))) < 0) {
                var9 = 0;
            }
            if (var9 > 10) {
                var9 = 10;
            }
            return var9;
        } else {
            return 0;
        }
    }

    public int stop() {
        if (!running)
            return 0;

        printLog("stopping");
        running = false;
        if (future != null) {
            try {
                future.get();
            } catch (Exception e) {
                Log.e(TAG, "stop exception", e);
            } finally {
                future = null;
            }
        }

        return 0;
    }

    public int playback() {

        stop();

        if (this.latestPath == null) {
            return -1;
        }

        printLog("playback starting");

        running = true;
        future = workerThread.submit(new Runnable() {

            @Override
            public void run() {

                RandomAccessFile file = null;

                int bufferSize = CHANNELS * FREQUENCY * BITS * INTERVAL / 1000 / 8;
                int minBufferSize = AudioTrack.getMinBufferSize(FREQUENCY, AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);

                if (minBufferSize > bufferSize) {
                    bufferSize = minBufferSize;
                }

                byte buffer[] = new byte[bufferSize];

                AudioTrack player = null;

                try {

                    player = new AudioTrack(AudioManager.STREAM_MUSIC, FREQUENCY, AudioFormat.CHANNEL_OUT_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);

                    file = new RandomAccessFile(latestPath, "r");
                    file.seek(44);

                    player.play();

                    printLog("playback started");

                    while (running) {

                        int size = file.read(buffer, 0, buffer.length);
                        if (size == -1) {
                            break;
                        }

                        player.write(buffer, 0, size);
                    }

                    player.flush();

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    try {
                        running = false;

                        if (player != null) {
                            if (player.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
                                player.stop();
                            }
                            player.release();
                        }

                        printLog("playback stoped");

                        if (file != null) {
                            file.close();
                        }
                    } catch (IOException e) {
                        // ignore
                    }
                }

            }
        });

        return 0;
    }

    private RandomAccessFile fopen(String path) throws IOException {
        File f = new File(path);

        if (f.exists()) {
            f.delete();
        } else {
            File parentDir = f.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
        }

        RandomAccessFile file = new RandomAccessFile(f, "rw");

        /* RIFF header */
        file.writeBytes("RIFF"); // riff id
        file.writeInt(0); // riff chunk size *PLACEHOLDER*
        file.writeBytes("WAVE"); // wave type

        /* fmt chunk */
        file.writeBytes("fmt "); // fmt id
        file.writeInt(Integer.reverseBytes(16)); // fmt chunk size
        file.writeShort(Short.reverseBytes((short) 1)); // format: 1(PCM)
        file.writeShort(Short.reverseBytes((short) CHANNELS)); // channels: 1
        file.writeInt(Integer.reverseBytes(FREQUENCY)); // samples per second
        file.writeInt(Integer.reverseBytes((int) (CHANNELS * FREQUENCY * BITS / 8))); // BPSecond
        file.writeShort(Short.reverseBytes((short) (CHANNELS * BITS / 8))); // BPSample
        file.writeShort(Short.reverseBytes((short) (CHANNELS * BITS))); // bPSample

        /* data chunk */
        file.writeBytes("data"); // data id
        file.writeInt(0); // data chunk size *PLACEHOLDER*

        printLog("wav path: " + path);
        return file;
    }

    private void fwrite(RandomAccessFile file, byte[] data, int offset, int size) throws IOException {
        file.write(data, offset, size);
        printLog("fwrite: " + size);
    }

    private void fclose(RandomAccessFile file) throws IOException {
        try {
            file.seek(4); // riff chunk size
            file.writeInt(Integer.reverseBytes((int) (file.length() - 8)));

            file.seek(40); // data chunk size
            file.writeInt(Integer.reverseBytes((int) (file.length() - 44)));

            printLog("wav size: " + file.length());

        } finally {
            file.close();
        }
    }

    private void printLog(String dd) {
        Log.d(TAG, Thread.currentThread().getName() + " :" + dd);
    }
}