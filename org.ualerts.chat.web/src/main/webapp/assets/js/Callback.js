/**
 * Object that calls a specified function from a specified context.
 * Taken from Michael Irwin (https://github.com/ualerts-org/ualerts-server/blob
 * 	/AFDP-99/org.ualerts.fixed/org.ualerts.fixed.web/src/main/webapp/assets/js/
 * Callback.js)
 * 
 * @param functionReference The function to be called
 * @param context The object to call the functionReference function
 */
function Callback(functionReference, context) {
  this.execute = function() {
    functionReference.apply(context, arguments);
  };
}