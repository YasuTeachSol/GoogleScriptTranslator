// Function to translate text from one language to another
function translateText(langFrom, langTo, text) {
  var translatedText = LanguageApp.translate(text, langFrom, langTo);
  return translatedText;
}

// Function to handle doGet requests (for testing)
function doGet(e) {
  var params = e.parameter;
  var langFrom = params.langFrom || 'en';
  var langTo = params.langTo || 'es';
  var textToTranslate = params.text || 'Hello, how are you?';

  var translatedText = translateText(langFrom, langTo, textToTranslate);

  // Create a JSON object with the translated text
  var response = {
    'translatedText': translatedText
  };

  // Convert the JSON object to a string
  var jsonResponse = JSON.stringify(response);

  // Set the Content-Type header to application/json
  var output = ContentService.createTextOutput(jsonResponse)
    .setMimeType(ContentService.MimeType.JSON);

  return output;
}
