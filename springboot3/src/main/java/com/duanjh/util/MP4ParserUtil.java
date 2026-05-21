package com.duanjh.util;

import org.mp4parser.Container;
import org.mp4parser.muxer.Movie;
import org.mp4parser.muxer.Track;
import org.mp4parser.muxer.builder.DefaultMp4Builder;
import org.mp4parser.muxer.container.mp4.MovieCreator;
import org.mp4parser.muxer.tracks.AppendTrack;

import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-06 周三 10:11
 * @Version: v1.0
 * @Description:
 */
public class MP4ParserUtil {

    public static void main(String[] args) throws Exception{
        List<Movie> movies = Arrays.asList(
                MovieCreator.build("C:\\Users\\Administrator\\Desktop\\1.mp4"),
                MovieCreator.build("C:\\Users\\Administrator\\Desktop\\2.mp4")
        );
        List<Track> videoTracks = new LinkedList<>();
        for (Movie m : movies) {
            for (Track t : m.getTracks()) {
                if ("vide".equals(t.getHandler())) videoTracks.add(t);
            }
        }
        Movie result = new Movie();
        result.addTrack(new AppendTrack(videoTracks.toArray(new Track[0])));
        Container out = new DefaultMp4Builder().build(result);
        FileChannel fc = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\merged.mp4").getChannel();
        out.writeContainer(fc);
        fc.close();
    }
}
