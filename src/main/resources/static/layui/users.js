layui.use(['table','element'], function(){
    var table = layui.table
        ,form = layui.form
        ,$ = layui.jquery
        ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

    //Hash地址的定位
    var layid = location.hash.replace(/^#test=/, '');
    element.tabChange('test', layid);

    element.on('tab(test)', function(elem){
        location.hash = 'test='+ $(this).attr('lay-id');
    });
    //监听锁定操作
    form.on('checkbox(lockDemo)', function(obj){
        //layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        $.post("/admin/unLock",{isLock: obj.elem.checked, uid:this.value}, function (res) {
            console.log(res);
            layer.msg(res.msg);
        })
        console.log(obj)
    });
    table.render({
        elem: '#test'
        ,url:'/admin/getUsers'
        ,toolbar: '#toolbarDemo'
        ,title: '用户数据表'
        ,cols: /*<![CDATA[*/[[
            {type: 'checkbox'}
            ,{field:'username', width:90, title: '用户名', sort: true}
            ,{field:'sex', width:60, title: '性别'}
            ,{field:'email', width:170, title: '邮箱'}
            ,{field:'addres', title: '地址', width: 150}
            ,{field:'phoneNum', title: '电话号码', width: 120}
            ,{field:'regTime', width:200, title: '注册时间'}
            ,{field:'role', width:70, title: '权限', sort: true}
            ,{field:'other', minWidth:150, title: '备注'}
            ,{field:'ucode', title:'是否锁定',templet: '#checkboxTpl', unresize: true, width:110}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:170}
            /*]]>*/]]
        ,page: true
        ,limit: 10
        ,loading: true
        ,toolbar: true
    });
    //头工具栏事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选': '未全选');
                break;
        };
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){
        var data = obj.data;
        console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                $.post("/admin/delUser",{uid:data.uid}, function (res) {
                    console.log(res)
                    if (res.code==200){
                        obj.del();
                        layer.close(index);
                    } else {
                        layer.msg(res.msg())
                    }
                });
            });
        } else if(obj.event === 'edit'){
            layer.prompt({
                formType: 2
                ,value: data.password
            }, function(value, index){
                obj.update({
                    password: value
                });
                console.log(obj)
                $.post("/user/newPass",{username:data.username, password: value}, function (res) {
                    layer.msg(res.msg);
                });
                layer.close(index);
            });
        }else if(obj.event == 'sele'){
            window.location = '/admin/user?username='+ data.username;
        }
    });
});