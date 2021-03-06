package com.twt.service.interactor;

import java.io.File;

/**
 * Created by sunjuntao on 16/2/20.
 */
public interface FoundInteractor {

    void getFoundList(int page);

    void getFoundDetails(int id);

    void uploadImage(File file);

    void postEdit(String authorization, int id, String title, String name, String time, String place, String phone, String content, String found_pic);

    void postFound(String authorizatioh, String title, String name, String time, String place, String phone, String content, String found_pic);

    void getMyFoundList(String authorization, int page);
}
