package cn.campusapp.router;

import android.content.Context;

import java.util.Queue;

import cn.campusapp.router.route.IRoute;
import cn.campusapp.router.router.ActivityRouter;
import cn.campusapp.router.router.BrowserRouter;
import cn.campusapp.router.router.HistoryItem;
import cn.campusapp.router.router.IActivityRouteTableInitializer;
import cn.campusapp.router.router.IRouter;
import timber.log.Timber;

/**
 * Created by kris on 16/3/17.
 * shell to the user
 */
public class Router {


    public static synchronized void addRouter(IRouter router){
        RouterManager.getSingleton().addRouter(router);
    }

    public static synchronized void initBrowserRouter(Context context){
       RouterManager.getSingleton().initBrowserRouter(context);
    }


    public static synchronized void initActivityRouter(Context context){
        RouterManager.getSingleton().initActivityRouter(context);
    }


    /**
     * @See
     * @param context
     * @param scheme
     * @param initializer
     */
    @Deprecated
    public static synchronized void initActivityRouter(Context context, String scheme, IActivityRouteTableInitializer initializer){
        RouterManager.getSingleton().initActivityRouter(context, initializer, scheme);
    }


    public static synchronized void initActivityRouter(Context context, IActivityRouteTableInitializer initializer, String ... scheme){
        RouterManager.getSingleton().initActivityRouter(context, initializer, scheme);
    }

    public static synchronized void initActivityRouter(Context context, String ... scheme){
        RouterManager.getSingleton().initActivityRouter(context, scheme);
    }

    public static boolean open(String url){
        return RouterManager.getSingleton().open(url);
    }

    public static boolean open(Context context, String url){
        return RouterManager.getSingleton().open(context, url);
    }

    /**
     * AndRouter uses Timber to output logs. Timber needs init, so if you don't use Timber and you want to view logs of AndRouter, you may need to
     * use this method, and set the debug as true
     */
    public static void setDebugMode(boolean debug){
        if(debug) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    /**
     * the route of the url, if there is not router to process the url, return null
     * @param url
     * @return
     */
    public static IRoute getRoute(String url){
        return RouterManager.getSingleton().getRoute(url);
    }


    public static boolean openRoute(IRoute route){
        return RouterManager.getSingleton().openRoute(route);
    }

    public static void setActivityRouter(ActivityRouter router){
        RouterManager.getSingleton().setActivityRouter(router);
    }

    public static void setBrowserRouter(BrowserRouter router){
        RouterManager.getSingleton().setBrowserRouter(router);
    }

    public static Queue<HistoryItem> getActivityChangedHistories(){
        return RouterManager.getSingleton().getActivityChangedHistories();
    }


}
