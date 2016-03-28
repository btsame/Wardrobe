package com.cy.yangbo.wardrobe.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by nianxin on 15/9/22.
 * <p/>
 * 对图片操作的代理
 */
public class CameraProxy {


    //相机核心类
    private CameraCore cameraCore;


    public CameraProxy(CameraResult cameraResult, Activity activity) {
        cameraCore = new CameraCore(cameraResult, activity);
    }


    //拍照
    public void getPhoto2Camera(String path) {
        Uri uri = Uri.fromFile(new File(path));
        cameraCore.getPhoto2Camera(uri);
    }


    //拍照截图
    public void getPhoto2CameraCrop(String path) {
        Uri uri = Uri.fromFile(new File(path));
        cameraCore.getPhoto2CameraCrop(uri);
    }


    //选择照片
    public void getPhoto2Album(String path) {

        Uri uri = Uri.fromFile(new File(path));
        cameraCore.getPhoto2Album(uri);
    }


    //选择照片，截图
    public void getPhoto2AlbumCrop(String path) {

        Uri uri = Uri.fromFile(new File(path));
        cameraCore.getPhoto2AlbumCrop(uri);
    }


    //接受ActivityResult
    public void onResult(int requestCode, int resultCode, Intent data) {
        cameraCore.onResult(requestCode, resultCode, data);
    }


}
