package protego.com.protego;

/**
 * Created by chanijindal on 24/12/14.
 */
public class AppsInfo {

    int uid;
    String username;
    String apps[];
    boolean selected;
    String storeString;

    public String cacheString()
    {
     int i;
        StringBuilder stringForCache= new StringBuilder();
        stringForCache.append(uid+":");
        for(i=0;i<apps.length;i++) {
            if (i != 0)
                stringForCache.append(",");
            stringForCache.append(apps[i]);
          }

        storeString=stringForCache.toString();
        return storeString;

    }


}
