package br.com.fatec.tg.gohorse.notes;

import android.app.Application;

import io.realm.Realm;

public class NotesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
