# GroupRecyclerView
分组RecyclerView

使用步骤：  
1、Adapter的onBindViewHolder方法中将组名绑定到itemview的tag上
```
holder.itemView.tag = lists[position].groupName
```

2、通过建造者模式构建组头
```
var groupItemDecoration = GroupItemDecoration.Builder(this, lists, MyComparator())
                .setDefaultGroupHeight(60)//设置组高
                .setLeftMargin(40) //设置组左边距
                .setRadius(15) // 设置圆半径
                .setTextSize(25) // 设置组文字大小
                .builder()
```
  
  
<img src="https://github.com/dalancon/GroupRecyclerView/blob/master/screenshot/Screenshot1.jpg" alt="Sample"  width="216" height="384"/>
