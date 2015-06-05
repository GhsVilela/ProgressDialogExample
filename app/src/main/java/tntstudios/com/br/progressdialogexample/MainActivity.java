package tntstudios.com.br.progressdialogexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mBtnStartProcess = (Button) this.findViewById(R.id.btn_start_process);

        mBtnStartProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressBar = new ProgressDialog(MainActivity.this);
                mProgressBar.setCancelable(false);
                mProgressBar.setTitle(getString(R.string.lbl_loading));
                mProgressBar.setMessage("iniciando");
                mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressBar.setMax(100);
                mProgressBar.setProgress(0);
                mProgressBar.show();

                ProcessData p = new ProcessData();
                p.execute(10);

            }
        });
    }

    public class ProcessData extends AsyncTask<Integer, String, String> {

        @Override
        protected String doInBackground(Integer... integers) {

            int progress = 0;
            int total = integers[0];

            while (progress <= total) {

                try {

                    Thread.sleep(2000); // 2 segundos

                } catch(InterruptedException e) {

                }

                String m = progress % 2 == 0 ? "dados usuário" : "contatos";

                // exibimos o progresso
                this.publishProgress(String.valueOf(progress), String.valueOf(total), m);

                progress++;
            }

            return "DONE";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            Float progress = Float.valueOf(values[0]);
            Float total = Float.valueOf(values[1]);

            String message = values[2];

            mProgressBar.setProgress((int) ((progress / total) * 100));
            mProgressBar.setMessage(message);

            // se os valores são iguais, termianos nosso processamento
            if (values[0].equals(values[1])) {
                // removemos a dialog
                mProgressBar.cancel();
            }
        }
    }
}
