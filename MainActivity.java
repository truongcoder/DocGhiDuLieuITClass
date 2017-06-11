package com.example.pctruong.docghidulieu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = (EditText) findViewById(R.id.edit);
    }

    public boolean KiemTraTheNhoChiDoc() {
        String kt = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(kt)) {
            return true;
        }
        return false;
    }

    public boolean KiemTraTheNhoDaGan() {
        String kt = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(kt)) {
            return true;
        }
        return false;
    }

    public void GhiFileTheNho(View view) {
        if (KiemTraTheNhoDaGan() == false) {
            Toast.makeText(this, "máy chưa gắn thẻ nhớ", Toast.LENGTH_SHORT).show();
        } else {
            if (KiemTraTheNhoChiDoc() == true) {
                Toast.makeText(this, "Chỉ đọc dl thẻ nhớ  ", Toast.LENGTH_SHORT).show();
            } else {
                File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/TheNho");//tao folder
                folder.mkdirs();//tạo ra folder nếu chưa có

                File filethenho = new File(folder.getPath(), "filethenho.txt");

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(filethenho);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    bufferedOutputStream.write(edit.getText().toString().getBytes());

                    bufferedOutputStream.flush();//đẩy dữ liệu sang hết
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void DocFileTheNho(View view) {
        if (KiemTraTheNhoDaGan() == false) {
            Toast.makeText(this, "máy chưa gắn thẻ nhớ", Toast.LENGTH_SHORT).show();
        } else {
            if (KiemTraTheNhoChiDoc() == true) {
                Toast.makeText(this, "Chỉ đọc dl thẻ nhớ  ", Toast.LENGTH_SHORT).show();
            } else {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/TheNho", "filethenho.txt");
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    InputStreamReader reader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String dulieu = "";
                    StringBuilder builder = new StringBuilder();
                    while ((dulieu = bufferedReader.readLine()) != null) {
                        builder.append(dulieu + "");
                    }
                    bufferedReader.close();
                    reader.close();
                    fileInputStream.close();
                    Toast.makeText(this, builder, Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void GhiFileVaoBoNhoTrong(View view) {
        try {
            FileOutputStream outputStream = openFileOutput("dulieu.txt", Context.MODE_PRIVATE);
            outputStream.write(edit.getText().toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DocFileTuBoNhoTrong(View view) {
        try {
            FileInputStream fileInputStream = openFileInput("dulieu.txt");
            String dulieu = "";
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            while ((dulieu = bufferedReader.readLine()) != null) {
                builder.append(dulieu + "");

            }
            bufferedReader.close();
            reader.close();
            fileInputStream.close();
            Toast.makeText(this, "" + builder, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GhiFileCache(View view) {
        File file = new File(getCacheDir().getPath() + "dulieu.txt");

        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(edit.getText().toString());
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DocFileCache(View view) {
        File file = new File(getCacheDir().getPath() + "dulieu.txt");
        String dulieu = "";
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            while ((dulieu = bufferedReader.readLine()) != null) {
                builder.append(dulieu + "");
            }
            bufferedReader.close();
            reader.close();
            Toast.makeText(this, "" + builder, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LuuAnhVaoBoNho(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dantruong);
        File file=new File(Environment.getExternalStorageDirectory().getPath()+ "hinhanh.png ");
        try {
            FileOutputStream os=new FileOutputStream(file);//tạo ra luồng lưu
            bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
            os.flush();
            os.close();
            Toast.makeText(this, "Lưu hình ảnh thành công", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
