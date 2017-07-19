package com.example.android6999.cadastro;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.ArrayList;

/**
 * Created by android6999 on 19/07/17.
 */

public class Permissao {

    private static final int CODE = 123;
    private static ArrayList<String> listaPermissoes = new ArrayList<>();

    public static void fazPermissao(Activity activity){

        String[] permissoes = {Manifest.permission.CALL_PHONE,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.INTERNET,
        Manifest.permission.CALL_PHONE};

        for (String permissao : permissoes){

            if (activity.checkCallingOrSelfPermission(permissao) != PackageManager.PERMISSION_GRANTED){

                listaPermissoes.add(permissao);
            }

        }
        request(activity);
    }

    private static void request(Activity activity){
        String[] array = listaPermissoes.toArray(new String[]{});

        if (listaPermissoes.size()>0){
            activity.requestPermissions(array, CODE);
        }
    }


}
