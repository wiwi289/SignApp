package swu.cx.drawerdemo.data.InforModel

data class AttendanceInfo(
    var kqstate:String,
    val kqid:String,
    var kqtemperature:String,
    val kqtype:String,
    val student:String,
    var kqtime:String,
    val studentname:String,
    var kqstarttime:String,
    var kqlocation:String
)