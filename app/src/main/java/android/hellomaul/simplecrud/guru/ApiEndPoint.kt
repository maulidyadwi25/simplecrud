package android.hellomaul.simplecrud.guru

class ApiEndPoint {
    companion object {
        private val SERVER = "https://simplecrudandroid3.000webhostapp.com/simplecrud/"
        val CREATE = SERVER + "create.php"
        val READ = SERVER + "read.php"
        val DELETE = SERVER + "delete.php"
        val UPDATE = SERVER + "update.php"
    }
}