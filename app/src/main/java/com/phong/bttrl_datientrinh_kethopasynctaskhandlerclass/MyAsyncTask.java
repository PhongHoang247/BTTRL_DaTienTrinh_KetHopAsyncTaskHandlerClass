package com.phong.bttrl_datientrinh_kethopasynctaskhandlerclass;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MyAsyncTask extends AsyncTask<Integer,Integer,ArrayList<Integer>> {
    private LinearLayout llRandom,llPrime;
    private Random random = new Random();
    private Activity context;
    public MyAsyncTask(Activity context){
        //Lấy các control trong Main Thread:
        this.llRandom = context.findViewById(R.id.llRandomNumber);
        this.llPrime = context.findViewById(R.id.llListPrime);
        this.context = context;
    }
    //Có thể cập nhật giao diện ở đây:
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context,"Bắt đầu",Toast.LENGTH_LONG).show();
        //khi bắt đầu thực thi tiến trình thì tiến hành xóa toàn bộ control bên trong
        this.llRandom.removeAllViews();
        this.llPrime.removeAllViews();
    }

    @Override
    protected void onPostExecute(ArrayList<Integer> integers) {
        super.onPostExecute(integers);
        //sau khi tiến trình kết thúc thì hàm này sẽ được thực thi
        //lấy về danh sách các số nguyên tố
        final ArrayList<Integer> list = integers;
        //tiến hành dùng Handler class để thực hiện
        final Handler handler = new Handler();
        Runnable target;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //lặp để vẽ các Button là số nguyên tố
                for (int i = 0; i < list.size(); i++){
                    final int x = list.get(i);
                    SystemClock.sleep(100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            doRawPrime(x);
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Kết thúc!",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        thread.start();
    }//end MyAsyncTask

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //lấy giá trị truyền vào trong doinbackground
        Integer item = values[0];
        //tạo Button với Text có giá trị là số ngẫu nhiên
        Button btn = new Button(context);
        btn.setWidth(100);
        btn.setHeight(30);
        btn.setText(item + "");
        //đưa button lên view bên trái màn hình
        this.llRandom.addView(btn);
    }

    //hàm kiểm tra số nguyên tố
    public boolean isPrime(int x){
        if (x < 2){
            return false;
        }
        for (int  i = 2; i <= Math.sqrt(x); i++){
            if (x % i ==0){
                return false;
            }
        }
        return true;
    }

    @Override
    protected ArrayList<Integer> doInBackground(Integer... integers) {
        int step = 1;
        ArrayList<Integer> list = new ArrayList<Integer>();
        //vòng lặp chạy hết n số button truyền vào
        int n = integers[0];
        while (isCancelled() == false && step <= n){
            step++;
            SystemClock.sleep(100);
            //lấy số ngẫu nhiên
            int x = random.nextInt(100) + 1;
            //gọi cập nhật giao diện
            publishProgress(x);
            //nếu là số nguyên tố thì lưu vào danh sách
            if (isPrime(x)){
                list.add(x);
            }
        }
        //trả về danh sách số nguyên tố
        return list;
    }

    //hàm vẽ các Button là chứac các số nguyên tố
    public void doRawPrime(int x){
        Button btn = new Button(context);
        btn.setWidth(100);
        btn.setHeight(30);
        btn.setText(x + "");
        //đưa Button vào view bên phải màn hình
        this.llPrime.addView(btn);
    }
}
