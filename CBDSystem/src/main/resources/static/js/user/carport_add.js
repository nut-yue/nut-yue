let pathDoc = 0;
let main = new Vue({
    el: "#main",
    data() {
        return {
            url: "/upload/userImg",
            form: {
                carportAddress: '',
                carportNumber: '',
                carportProperty: ''
            },
            value7: '',
            fileList2: []
        }
    },
    methods: {
        addCarport() {

        },
        handleChange(file, fileList2, type) {
            //  限制单张上传，超过限制即覆盖
            if (fileList2.length > 1) {
                fileList2.splice(0, 1);
            }
        },
        open2() {
            this.$message({
                message: '成功申请',
                type: 'success'
            });
        },
        open3() {
            this.$message({
                message: '添加失败！',
                type: 'warning'
            });
        },
        // beforeUpload (file) {
        //     const formData = new FormData();
        //     formData.append("carportAddress",main.form.carportAddress);
        //     formData.append("carportNumber",main.form.carportNumber);
        //     formData.append("carportProperty",main.form.carportProperty);
        //     formData.append('file', this.file);
        //     main.picData=formData;
        //     console.log("=========>>"+picData);
        // },
        onSubmit() {
            console.log('submit!');
            this.$refs.uploadFile.submit();
        },
        onSuccessDoc(response, file, fileList2) {
            pathDoc = response.data.path
            this.$refs.upload.submit();
        },
        onSuccess(response, file, fileList2) {
            console.log(file);
            console.log("fileList2==========" + fileList2);
            let pic1 = response.data.path;
            let pic2 = 0;
            $.ajax({
                type: "get",
                url: "../../carport/saveCarport",
                data: {
                    "carportAddress": this.form.carportAddress
                    , "carportNumber": this.form.carportNumber
                    , "carportProperty": this.form.carportProperty
                    , "carportPropertyDoc": pathDoc
                    , "carportImage": response.data.path
                },
                dataType: "json",
                success: function (resultData) {
                    console.log(resultData);
                    if (resultData != 0) {
                        main.open2();
                        main.$refs.uploadFile.clearFiles();
                        main.$refs.upload.clearFiles();
                        main.form.carportAddress = "";
                        main.form.carportNumber = "";
                        main.form.carportProperty = "";

                    } else {
                        main.open3();
                    }
                }
            })
        },
        handleRemove(file, fileList2) {
            console.log(file, fileList2);
        },
        handlePreview(file) {
            console.log(file);
        }
    }
})
