package com.example.codingboy.zhihupaper.domin;

import java.util.Arrays;

/**
 * Created by codingBoy on 16/8/12.
 */
public class WebContent
{
    public String body;
    public String[] css;
    public String ga_prefix;
    public String id;
    public String image;
    public String image_source;
    public String[] images;
    public String[] js;
    public String share_url;
    public String title;
    public String type;


    @Override
    public String toString()
    {
        return "WebContent{"  +
                ", css=" + Arrays.toString(css) +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", image_source='" + image_source + '\'' +
                ", images=" + Arrays.toString(images) +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
