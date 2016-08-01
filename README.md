# RetrofitCacheCallback
# retrofitcachecallback_version.jar

CacheCallback implements Retrofit Callback and using SharedPreferences to store data. Use it instead of Retrofit default callback.



Dependencies:
```gradle
compile "com.squareup.retrofit2:retrofit:2.1.0"

compile "com.squareup.retrofit2:converter-gson:2.1.0"
```


Constructors:
```java

public CacheCallback(Context context, Class bodyClass, boolean useCache, Gson gson)

public CacheCallback(Context context, Class bodyClass, boolean useCache)

public CacheCallback()
```



Example:

with cache:
```java
retrofit.create(RandomService.class).getRandomNumber()
  .enqueue(new CacheCallback<RandomNumber>(MainActivity.this, RandomNumber.class, true) {
    @Override
    public void success(RandomNumber body, boolean fromCache) {
      // POSITIVE RESPONSE
    }

    @Override
    public void failure(Call<RandomNumber> call, Throwable throwable) {
      // NEGATIVE RESPONSE
    }
  });
```

without cache:
```java
retrofit.create(RandomService.class).getRandomNumber()
  .enqueue(new CacheCallback<RandomNumber>() {
    @Override
    public void success(RandomNumber body, boolean fromCache) {
      // POSITIVE RESPONSE
    }

    @Override
    public void failure(Call<RandomNumber> call, Throwable throwable) {
      // NEGATIVE RESPONSE
    }
  });
```

To manually edit cache:

CacheCallbackPrefs.java

save
```java
void saveToCache(Context context, String url, String json)
```

read
```java
String readFromCache(Context context, String url)
```

remove
```java
void removeAllFromCache(Context context)
```
