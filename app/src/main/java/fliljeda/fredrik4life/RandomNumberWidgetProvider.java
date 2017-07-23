package fliljeda.fredrik4life;

import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by fredrik on 2017-07-23.
 */

public class RandomNumberWidgetProvider extends AppWidgetProvider{
    private static final String GET_RANDOM_NUMBER = "fliljeda.fredrik4life.getRandomNumberForWidget";
    private static final String INCREASE_LOW = "fliljeda.fredrik4life.increaseLow";
    private static final String DECREASE_LOW = "fliljeda.fredrik4life.decreaseLow";
    private static final String INCREASE_HIGH = "fliljeda.fredrik4life.increaseLow";
    private static final String DECREASE_HIGH = "fliljeda.fredrik4life.decreaseLow";

    private static int widget_low = 0;
    private static int widget_high = 10;
    private static int widget_random = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.randomnumber_widget);


            views.setOnClickPendingIntent(R.id.widget_button_roll, getPendingIntent(context, GET_RANDOM_NUMBER));
            views.setOnClickPendingIntent(R.id.widget_button_increase_low, getPendingIntent(context, INCREASE_LOW));
            views.setOnClickPendingIntent(R.id.widget_button_decrease_low, getPendingIntent(context, DECREASE_LOW));
            views.setOnClickPendingIntent(R.id.widget_button_increase_high, getPendingIntent(context, INCREASE_HIGH));
            views.setOnClickPendingIntent(R.id.widget_button_decrease_high, getPendingIntent(context, DECREASE_HIGH));





            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private PendingIntent getPendingIntent(Context context, String action){
        // Create an Intent to send get random number broadcast
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        return pendingIntent;
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);

        if(intent.getAction().equals(GET_RANDOM_NUMBER)){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.randomnumber_widget);
            ComponentName componentName = new ComponentName(context, RandomNumberWidgetProvider.class);

            widget_random = RandomNumberActivity.getRandomNumber(widget_low,widget_high);
            views.setTextViewText(R.id.widget_text_random, "" + widget_random);

            appWidgetManager.updateAppWidget(componentName,views);
        }else if(intent.getAction().equals(INCREASE_LOW)){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.randomnumber_widget);
            ComponentName componentName = new ComponentName(context, RandomNumberWidgetProvider.class);

            widget_low++;
            views.setTextViewText(R.id.widget_text_low, "" + widget_low);

            appWidgetManager.updateAppWidget(componentName,views);
        }else if(intent.getAction().equals(DECREASE_LOW)){


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.randomnumber_widget);
            ComponentName componentName = new ComponentName(context, RandomNumberWidgetProvider.class);

            widget_low--;
            views.setTextViewText(R.id.widget_text_low, "" + widget_low);

            appWidgetManager.updateAppWidget(componentName,views);
        }else if(intent.getAction().equals(INCREASE_HIGH)){


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.randomnumber_widget);
            ComponentName componentName = new ComponentName(context, RandomNumberWidgetProvider.class);

            widget_high++;
            views.setTextViewText(R.id.widget_text_high, "" + widget_high);

            appWidgetManager.updateAppWidget(componentName,views);
        }else if(intent.getAction().equals(DECREASE_HIGH)){


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.randomnumber_widget);
            ComponentName componentName = new ComponentName(context, RandomNumberWidgetProvider.class);

            widget_low--;
            views.setTextViewText(R.id.widget_text_high, "" + widget_high);

            appWidgetManager.updateAppWidget(componentName,views);
        }
    }

}
