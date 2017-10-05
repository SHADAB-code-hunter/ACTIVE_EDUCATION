package services;

import android.util.Log;

import java.util.ArrayList;

import callbacks.BoxOfficeMoviesLoadedListener;
import logging.L;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;
import pojo.Movie;
import pojo.Notif;
import task.TaskLoadMoviesBoxOffice;

/**
 * Created by Windows on 23-02-2015.
 */
public class ServiceMoviesBoxOffice extends JobService implements BoxOfficeMoviesLoadedListener {
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("service_box", "onStartJob");
        L.t(this,"onstartjob");
        this.jobParameters = jobParameters;
      //  new TaskLoadMoviesBoxOffice(this).execute();

        jobFinished(jobParameters, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("stopservice_box", "onStopJob");
        return true;
    }


    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Notif> listMovies) {
        L.t(this, "onBoxOfficeMoviesLoaded");
        //jobFinished(jobParameters, false);
    }

    @Override
    public void on_id_listener(String str_id) {

    }
}

