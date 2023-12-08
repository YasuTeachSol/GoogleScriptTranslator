# GoogleScriptTranslator Android Library

This Android library allows you to easily integrate Google Apps Script for text translation in your Android applications. It uses OkHttp for making HTTP requests and provides a convenient interface for handling translation callbacks.

## Usage

1. **Setup:**
   - Include the `GoogleScriptTranslator` class in your Android project.
   - Ensure that the OkHttp library is added to your project dependencies.

      # Download

   * Step 1. Add it in your root build.gradle at the end of repositories:
     ```groovy
     allprojects {
         repositories {
             ...
             maven { url 'https://jitpack.io' }
         }
     }
     ```
   * Step 2. Add the dependency
     ```groovy
     dependencies {
         implementation 'com.github.YasuTeachSol:GoogleScriptTranslator:1.0'
         implementation 'com.squareup.okhttp3:okhttp:4.9.0' // Use the latest stable version of OkHttp
     }
     ```

2. **Permissions:**
   - Add the internet permission to your `AndroidManifest.xml` file:
     ```xml
     <uses-permission android:name="android.permission.INTERNET" />
     ```

3. **Usage in MainActivity:**
   ```java
   public class MainActivity extends AppCompatActivity {

       // Constants
       private static final String TAG = "MainActivity";
       private static final String SCRIPT_ID = "YOUR_GOOGLE_SCRIPT_ID";
       private static final String SOURCE_LANG = "en"; // Source language code
       private static final String TARGET_LANG = "am"; // Target language code

       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);

           // Text to translate
           String textToTranslate = "Hello, how are you?";

           // Translate using GoogleScriptTranslator
           GoogleScriptTranslator.translate(SCRIPT_ID, SOURCE_LANG, TARGET_LANG, textToTranslate, new GoogleScriptTranslator.TranslationCallback() {
               @Override
               public void onTranslationComplete(String result) {
                   // Handle the translated text
                   Log.d(TAG, "Translation Result: " + result);
               }

               @Override
               public void onTranslationError(String error) {
                   // Handle the error
                   Log.e(TAG, "Translation Error: " + error);
               }
           });
       }
   }
