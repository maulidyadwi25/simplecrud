package android.hellomaul.simplecrud.pegawai

class ApiEndPointPegawai {
    companion object {
        private val SERVER = "https://simplecrudandroid3.000webhostapp.com/pegawai/"
        val CREATE = SERVER + "create.php"
        val READ = SERVER + "read.php"
        val DELETE = SERVER + "delete.php"
        val UPDATE = SERVER + "update.php"
    }
}