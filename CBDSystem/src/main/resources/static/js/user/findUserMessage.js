<!--周陆成-->
let findmsg = new Vue({
    el: '#usermessage',
    data() {
        return {
            tableData: [],
            //分页 每次翻多少页
            currentPage: 1,
            pageSize: 10,
            total: 100,
            //模态框信息
            dialogFormVisible1: false,
            options: [{
                value: '1',
                label: '已读'
            }, {
                value: '0',
                label: '未读'
            }],
            value: "",
            messageType: -1,
            form: {
                messageTitle: '',
                messageContent: '',
                messageTime: '',
                messageType: '',
                messageIsRead: '',
                messagePostUserId: '',
                messagePostUserBean: {
                    userRealName: ''
                }
            },
            formLabelWidth1: '120px',
            formInline: {
                user: ''
            }

        }
    },
    methods: {
        initList() {
            $.ajax({
                type: "get",
                url: "../../message/myTakeMessage",
                data: {
                    "messageIsRead": -1,
                    "currentPage": findmsg.currentPage,
                    "pageSize": findmsg.pageSize,
                    "messageIsRead": findmsg.messageType
                },
                dataType: "json",
                success: function (resultData) {


                    if(resultData.code==200){
                        findmsg.tableData = resultData.data.messages.records;
                        findmsg.total = resultData.data.messages.total;
                        //console.log(resultData)
                    }else{
                        findmsg.$message.error('获取数据错误')
                    }

                }
            })
        },
        update() {
            $.ajax({
                type: "get",
                url: '../../message/updateMessages',
                data: {
                    "messageidStr": messageId,
                    "messageIsRead": 1
                },
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        findmsg.initList();
                    } else {
                        findmsg.$message.error('获取数据错误')

                    }

                }
            })
        },
        handleEdit(index, row) {
            findmsg.dialogFormVisible1 = true;
            $.ajax({
                type: "get",
                url: '../../message/findById',
                data: {"messageId": row.messageId},
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData)
                    findmsg.form = resultData.data.message;
                    messageIsRead = resultData.data.message.messageIsRead;
                    messageId = resultData.data.message.messageId;
                    if (resultData.data.message.messageIsRead == 0) {
                        findmsg.update();
                    }
                }
            })
        },
        handleDelete(index, row) {

            this.$alert('是否永久删除该消息?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.currentPage = 1,
                    $.ajax({
                        type: "get",
                        url: '../../message/deleteMessages',
                        data: {"messageId": row.messageId},
                        dataType: "json",
                        success: function (resultData) {
                            if (resultData.code == 200) {
                                findmsg.initList();
                            } else {
                                findmsg.$message.error('获取数据错误')
                            }
                        }
                    })
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            findmsg.pageSize = val;
            findmsg.initList();
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            findmsg.currentPage = val;
            findmsg.initList();
        },
        onSubmit() {
        },
        find() {
            if (findmsg.value != null) {
                findmsg.messageType = findmsg.value;
                findmsg.initList();
            } else {
                $.ajax({
                    type: "get",
                    url: "../../message/myTakeMessage",
                    data: {
                        "messageIsRead": -1,
                        "currentPage": findmsg.currentPage,
                        "pageSize": findmsg.pageSize
                    },
                    dataType: "json",
                    success: function (resultData) {
                        if (resultData.code == 200) {
                            findmsg.tableData = resultData.data.messages.records;
                            findmsg.total = resultData.data.messages.total;
                        } else {
                            findmsg.$message.error('获取数据错误')
                        }
                    }
                })

            }
        }
    }
});
findmsg.initList();
var messageIsRead = 0;
var messageId = 0;
