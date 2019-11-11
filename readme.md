*Author: WindVision*

#### 1.Brief Intro

* * *

Isolation Forest is an anomaly detection algorithm. A forest consists of several isolation trees. Isolation tree is a binary tree similar to BST, where closer the node is to the root more anomaly the node indicating the data is. We calculate the path length of nodes of each tree and take the average, then get score of each point according to the reference's algorithm. The data is more likely to be anomaly point if the value is close to 1.

#### 2. Key Algorithm description
***
##### 2.1 Create isolation Tree
***
**Inputs**: *X* - input data *e* - current tree height *l* - height limit

**Output**: an iTree

1: **if** e>=l or |X|<=1 **then**

2:  return exNode{Size = |X| }

3:**else**

4:  let Q be a list of attributes in *X*

5:  randomly select an attribute q of Q

6:  randomly select a split point *p* from *max* and *min* values of attribute *q* in *X*

7:  Xl = *filter* (*X*,*q*<*p* )

8:  Xr = *filter*(*X*,*q*>=*p*)

9:  return *inNode* {LeftItree = CreateiTree(Xl,*e*+1,*l* ) ,

10:                      RightItree = CreateiTree(Xr,*e*+1,*l* ),

11:                      SplitAtt = *q*

12:                      SplitValue = *p* }

13: **end if**

##### 2.2 Create Isolation Forest
***

**Inputs**: *X* - input data, *t* - number of trees, *n*-subsampling size

**Output**: a set of *t* iTrees

1:**Initialize** Forest

2: set height limit *l* = *ceiling* ( log2(n) )

3: **for** *i* = 1 to *t* **do**

4: *X'* = sample( X, n )

5: Forest = Forest U itree( *X'*,0,l)

6: **endfor**

7: **return** Forest 

##### 2.3 Path length and scoring
***
**Inputs** : *x* - an instance, *T* - an iTree, *e* - current path length; to be initialized to zero when first called 

**Output**: path length of *x*

1: **if** *T* is an external node then 

2: return *e* + c(T.size) 

3: **end if** 

4: a ← T.splitAtt 

5: **if** x(a) < *T*.*splitValue* **then** 

6: return PathLength( *x*, *T*.left, *e* + 1)

7: **else** {x(a) ≥ *T*.*splitValue* } 

8:return PathLength( *x* ,*T*.right, *e* + 1)

9: **end if**

Then use the equation in the reference file to calculate the score.

##### 2.4 After scoring
***
We take the strategy of clustering the score into two. Specifically the max and min value of score are chosen to be the initial centroid instead of randomly picking, so as to more accurately cluster the points. 


#### 3. How to use?
***
First, you need to put your data in a 2D array, then call *createForest* function. There are three parameters in the function. The first one is your data array. The second one is the tree number in the forest, usually value 100 is appropriate. The last one is the subsampling size, which means you don't need to use all the data to set up a single isolation tree. Frequently we take value 256 as the subsampling size. The function returns an isolation forest.

So the normal usage of *createForest* is *iTree = createForest ( data, 100, 256)*

After creating an isolation forest, we need to label these data as normal or anomalies by function *label*. The function returns a 1D array of 0 or 1 where 1 indicates the anomalies. There is only one parameter in the function, meaning times doing clustering. Usually 10-50 is okay.

#### 4. Contact me
Any issues, contact luisliuzijie@foxmail.com
