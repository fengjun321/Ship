本项目为了降低运行难度，大家只需要运行
EureKaServer 和 provider

实验条件（优先做maccc ）住反对法
1.安装mysql，sql备份还原
2.下载安装idea
3.从git clone springcloud项目
4.用idea打开springcloud.iml文件
5.分别运行EureKaServer 和 provider

目前暂时有的测试命令(均为post请求)==》推荐使用软件postman
http://localhost:8010/Ship/register
{
        "username":"zhutgu",
        "password":2,
        "authority":"4545"
}

http://localhost:8010/Ship/login
{
        "username":"huoying",
        "password":"dd"
}

http://localhost:8010/Ship/checkeffect
{
        "tookenId": "9f4b93fec294a015f857955364cc982b"
}

http://localhost:8010/Ship/getshipticket
{}

http://localhost:8010/Ship/getAllRoom
{
    "shipId":1101
}
返回:
{
    "errcode": 0,
    "data": {
        "shipId": 1101,
        "cashPledge": 100,
        "priceLev": [
            20,
            30,
            40
        ],
        "reserve": [
            3,
            4
        ]
    }
}

http://localhost:8010/Ship/buyTicket
{
        "tookenId" : "9f4b93fec294a015f857955364cc982b",
        "ticketId" : 1101，
        "roomId":1
}

http://localhost:8010/Ship/getUserAllTicket
{
        "tookenId": "9f4b93fec294a015f857955364cc982b"
}

http://localhost:8010/Ship/getRecordCost
{}
