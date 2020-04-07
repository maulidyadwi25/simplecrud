package android.hellomaul.simplecrud.inventaris

class ApiEndPointInventaris {
    companion object {
        private val SERVER = "https://simplecrudandroid3.000webhostapp.com/inventaris/"
        val CREATE = SERVER + "create.php"
        val READ = SERVER + "read.php"
        val DELETE = SERVER + "delete.php"
        val UPDATE = SERVER + "update.php"
    }
}