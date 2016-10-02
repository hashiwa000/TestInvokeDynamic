# TestInvokeDynamic

Result is as following:

```
<<run: #01>>
create: TestInvokeDynamic
In bootstrap method: TestInvokeDynamic, adder, (Object,Object)Object
<<result: 3>>
<<run: #02>>
<<result: 5>>
<<run: #03>>
create: TestInvokeDynamic2
In bootstrap method: TestInvokeDynamic2, adder, (Object,Object,Object)Object
<<result: 6>>
<<run: #04>>
create: TestInvokeDynamic3
In bootstrap method: TestInvokeDynamic3, adder, (Object,Object)Object
In bootstrap method: TestInvokeDynamic3, adder, (Object,Object)Object
<<result: 6>>
```

