# Algorithms_Princeton

所有代码为Coursera公开课

All the codes are Coursera open course

* [Algorithms, Part I](https://www.coursera.org/learn/algorithms-part1) 
* [Algorithms, Part II](https://www.coursera.org/learn/algorithms-part2)

的作业代码。

assignment codes.

---

## Contents

### Algorithms, Part I

* WEEK 1: [percolation](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/percolation)
* WEEK 2: [queues](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/queues)
* WEEK 3: [collinear](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/collinear)
* WEEK 4: [8puzzle](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/8puzzle)
* WEEK 5: [kdtree](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/kdtree)

### Algorithms, Part II

* WEEK 1: [wordnet](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/wordnet)
* WEEK 2: [seam](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/seam)
* WEEK 3: [baseball](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/baseball)   
* WEEK 4: [boggle](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/boggle)
* WEEK 5: [burrows](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/burrows)

---

## 代码分析 Code Analysis

### Algorithms, Part I

#### Week 1 percolation
Percolation

* [specification](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/percolation/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/percolation)


最关心的问题是，怎么样才能避免循环检测起始点，使得percolates()尽可能地快？我最初的想法是这样的
* 全局一共有$n^2+2$个点。
* 让virtual top是第$n^2+1$个点，virtual bottom是第$n^2+2$个点--其实按照自己喜好的来，不一定非是这两个数字。
* 建立对象的时候，
把第$1,2,\cdots, n$个点跟第$n^2+1$个点连起来，
把第 $n^2-n+1, n^2-n+2,\cdots,n^2$个点跟$n^2+2$连起来。
* 每次只需检验第$n^2+1$个点和第$n^2+2$个点是不是连通的，就可以知道是不是percolate的。

但是这样的想法有一个缺点，就是无法避免**backwash**的情况。
首先，我来解释一下**backwash**是什么意思：
假设A是我们要求证是否full的点，A与第一排的点都不相连，那么isFull的结果应该为false。
但是与virtual bottom相连，而virtual top和virtual bottom通过别的点相连，此时结果是没有错 整体的确是percolates的。但是这样就会导致A与virtual top相连，我们得到A处isFull的结果就是true。

所以最简单的办法：我们把最后一个点排除掉，再建一个新的只有$n^2+1$个点的WeightedQuickUnionUF。对这两个WeightedQuickUnionUF我们采用相同的方式连接节点。每次调用isFull()的时候，就在新的WeightedQuickUnionUF里面检查。

**但是** 这样会导致时间空间表现不好。

那么怎么办呢，其实可以有别的办法来表示virtual bottom，那么只需要建立一个包含$n^2+1$个点的unionfind。
例如，可以建立一个矩阵，
* 当该点closed时，对应结果为0；
* open但是不与virtual bottom相连时为1；
* open且与virtual bottom相连时为2。

每一次在执行open()函数时，依次检查该点或相邻open点的根root是否为2。如果是 就把新形成的根设为2，这样对于每一个点 只需要检查它们的根是否为2。
只要根为2，那么这一个连通分支都是与virtual bottom相连的。


---


#### Week 2 Deque
Deques and Randomized Queues


* [specification](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/queues/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/queues)


这个问题的难点：究竟采用什么样的数据结构，能够使得在RandomizedQueue中，删除这个操作时间是constant呢？
(Specification原文：Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time.)）

无论是链表还是数组，在删除这一步都无法做到constant time吧?
链表：需要一个一个地找元素
数组：删除完毕之后要把之后的元素一个一个地前移
万幸的是，这RandomizedQueue有randomized的性质，所有的操作都跟元素摆放顺序无关。
那么我们就可以采用数组，每一次删除的时候，先随机选一个元素删除，再用最后一个元素去补被删除的这个元素的位置，并令size--即可。
甚至，我们不需要设置去补位的最后一个元素重新为null，因为size已经缩短了。当新元素出现的时候，这个位置会被新的元素重新覆盖掉。

---

#### Week 3 collinear
Collinear Points

* [specification](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/collinear/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/collinear)


难点：
* 怎么不重不漏地找共线点？
* 怎么让那些共线的点乖乖排成我们想要的顺序，这样我们好提取头尾组建LineSegement?

最傻的办法，当然可以每一次找到新的一组点时，都检查一下这组点是不是之前已经找到过了，如果已经找到过了，那么就直接扔掉。每一次找到一组共线点的时候，都sort一次，提取头尾。

显然这样是非常费时的。实际上，我们可以在所有操作开始之前，对所有点先排序一次。
此外，FastCollinearPoints.java 中需要使用Comparator slopeOrder()来排序，可以使用函数
sort (T[] a, Comparator<? super T> c)
这里，sort是可以使用Comparator作为输入的。

---

#### Week 4 8puzzle
8 puzzle

* [specification](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/8puzzle)


A$^\star$ algorithm基本上就是老是把邻居们往MinPQ里面塞，然后一直取出最小的那个，直到MinPQ变成空的。在这个过程中其实并不需要过分考虑Board是否被evaluete过，只需要不回到上一个就可以了，这样编程可以省去不少麻烦。

难点：
* MinPQ怎么知道要根据什么最小值来筛选呢？ -- 所以你要写一个Comparator，然后用这个Comparator生成MinPQ。
* 如果我们的Board不是solvable的，那么这个MinPQ岂不是没完没了了？-- 幸运的是，我们知道一个Board和它的twin之中，必有一个Solvable，一个不是Solvable的，那么当我们发现Twin是Solvable的，就可以确定我们这个不是Solvable的了。
* 我不能走回头路，那么我就不能回到之前来的那个neighbor那里去，那我用什么数据结构记录它呢？-- 所以要写Node，这个Node中还要包含preNode。

为了执行delMin()，每一次计算最小值，我还需要moves，所以Node中moves也是必不可少的。
事实上，我还建议Node中储存相应的manhattan值，而不是每一次都call Board中的函数，这样会省下不少时间。

---

#### Week 5 kdtree

* [specification](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/kdtree)


其实这是知易行难的一次作业，诶，但是我说我要写总结，我就来胡说八道一通吧。
* 首先当然是要用checklist里面推荐的node格式啦，接下来的过程跟Red-black BST的过程是非常类似的（当然我们这里并不需要考虑跟颜色相关的任何事情）。但是KdTree有一个特点，就是每一层会换一个分隔方向，也就是说每一层所需要比较的坐标都不同。这就要求我们需要额外引入一个布尔值，对此可以写一个if语句，比较不同的坐标以及相应的其他操作。在每一次使用递归函数进入到下一层的时候，将这个布尔值取反传送进去。当然，记录层数，并且取对2的余数也是可以的，但是不如这里方便。（如果k>3，大概就只能取k的余数了，至少我没想出来别的办法。）
* insert() 模仿 PPT Balanced Search Trees P37 put() ，注意这里，每个点对应的rect在递推函数中应有所体现，并且这里不需要rotate color。
* contains() 模仿 PPT Balanced Search Trees P21 get() ，虽然我们并不需要get value，但是找到那个点的过程是十分类似的。
* construct 一个RectHV前，仔细想想是否已经有现成的可以直接调用了，这样可以省下一些时间空间。
* Range search. 我们的specification里面是这样写的：if the query rectangle does not intersect the rectangle corresponding to a node, there is no need to explore that node (or its subtrees). A subtree is searched only if it might contain a point contained in the query rectangle. 所以显然递推函数里面该干的事情就是，检查现在这个节点行不行，记录；检查左子树是否为空且矩形是否有交集，若满足条件进入左子树的递归；检查右子树是否为空且矩形是否有交集，若满足条件进入右子树的递归。
* Nearest-neighbor search. 我们的specification里面是这样写的: if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees). That is, search a node only only if it might contain a point that is closer than the best one found so far. The effectiveness of the pruning rule depends on quickly finding a nearby point. To do this, organize the recursive method so that when there are two possible subtrees to go down, you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree. 所以首先应该比较现节点跟已知最近的点哪一个最好，记录；检查记录层数奇偶的布尔值，比较相应的坐标；若输入的点在现节点的左边/下边，如果左子树非空，那么就去左边递归一下；如果右子树的矩形到输入点的距离比目前已知最近点到输入点的距离更近，就去右边递归一下。因为如果更远，那么右子树里面一定不存在更近的点了，就没有必要再去看看右子树了；另一个方向亦然。
* 永远记得考虑输入参数为空，树为空，节点为空，节点相等等这些特殊情况。

说了这么多可能并没有什么用，毕竟递推函数就是这么难写。
**就算懂得好多道理，依然过不好这一生。**


---



### Algorithms, Part II


#### Week 1 wordnet
WordNet
* [specification](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/wordnet)


#### Week 2 seam
SeamCarving
* [specification](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/seam/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/seam)


#### Week 3 baseball
Baseball Elimination
* [specification](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/baseball/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/baseball)


#### Week 4 boggle
Boggle
* [specification](https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/boggle/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/boggle)

#### Week 5 burrows
Burrows_Wheeler
* [specification](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)
* [FAQ](https://coursera.cs.princeton.edu/algs4/assignments/burrows/faq.php)
* [my code](https://github.com/zhangyixing1007/Algorithms_Princeton/tree/master/burrows)

稍后更新Algorithms, Part II代码分析，敬请期待。

Code analysis of Algorithms, Part II will be updated soon.
