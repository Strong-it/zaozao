## DOM：Document Object Model(文本对象模型)
- D：文档 – html 文档 或 xml 文档
- O：对象 – document 对象的属性和方法
- M：模型 
- DOM 是针对xml(html)的基于树的API。
- DOM树:节点（node）的层次。
- DOM 把一个文档表示为一棵家谱树（父，子，兄弟）
- DOM定义了Node的接口以及许多种节点类型来表示XML节点的多个方面

## 节点及其类型
`<p title="a gentle reminder">Don't forget to buy this stuff.</p>`
1. 元素节点`P`
2. 属性节点：元素的属性，可以直接通过属性的方式来操作`title="a gentle reminder"`
3. 文本节点：是元素节点的子节点`Don't forget to buy this stuff.`

## js的位置
- 通常位于\<title>节点下面\<\head>节点上面「推荐」
- `<script type="text/javascript"> </script>`
```
<script type="text/javascript">
// 1. 当整个HTML文档加载成功后触发window.onload事件
// 事件触发时，执行后面的function函数体
window.onload = function() {
    // 2. 获取所有的button节点根据表签名
    var btn = documnet.getElementsByTagName("button")[0];
    // 3. 为button的onclick事件赋值：当点击button时，执行函数体
    btn.onclick = function() {
        // 4. 弹出helloworld
        alert("helloworld");
    }
}
</script>
```

## html和js代码耦合在一起
`<button onclick="alert(helloworld)">ClickMe</button>`

>注意：一般地，不能在body节点之前来直接获取body内的节点，因为此时html文档还没加载完成，获取不到指定的节点