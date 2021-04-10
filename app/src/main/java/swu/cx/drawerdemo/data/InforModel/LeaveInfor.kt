package swu.cx.drawerdemo.data.InforModel

data class LeaveInfor(
    val qjid:String,
    val student:String,
    val studentname:String,
    val qjday:String,//请假天数
    val reviewtime:String,
    val xjtime:String,//销假时间
    val qjcause:String,//请假原因
    val xj:String,//销假老师ID
    val qjstate:String,
    val qjtimeend:String,
    val qjtimechange:String,//申请时间
    val qjtimestart:String,
    val fdy:String,
    val location:String,//请假去向
)