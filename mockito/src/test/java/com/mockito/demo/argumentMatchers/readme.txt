This tutorial shows how to use the ArgumentMatcher and how it differs from the ArgumentCaptor.

We need to add a single artifact:(But here we are using spring boot application, so all dependencies are taken care)

<dependency>
    <groupId>org.mockito</groupId> 
    <artifactId>mockito-core</artifactId>
    <version>2.21.0</version> 
    <scope>test</scope>
</dependency>


ArgumentMatchers:
-----------------

Configuring a mocked method in various ways is possible. One of them is to return fixed values:

doReturn("Flower").when(flowerService).analyze("poppy");

In the above example, the String “Flower” is returned only when the analyze service receive the String “poppy”.
But we need to handle wider range of values, we can configure our mocked methods with argument matchers as below.

when(flowerService.analyze(anyString())).thenReturn("Flower");


anyString() -- ArgumentMatcher

Now, because of the anyString argument matcher, the result will be the same no matter what value we pass to flowerService.analyze() method. 
ArgumentMatchers allows us flexible verification or stubbing.

*** Note:Mockito requires you to provide all arguments either by matchers or by exact values.

abstract class FlowerService {
    public abstract boolean isABigFlower(String name, int petals);
}
 
FlowerService mock = mock(FlowerService.class);
 
when(mock.isABigFlower("poppy", anyInt())).thenReturn(true);