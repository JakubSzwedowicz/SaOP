# SaOP
Project contains applications, algorithms and sollutions to problems described below


# Projects
## List 1

**D1** - Determine affiliation of point P(x, y), knowing it's coordinates, ro colored zones of plane or to the background.

**D2** - Print three given numbers A, B and C  in ascending order.

**D3** - Sort three given numbers A, B and C in acending order. What is the difference between this and previous (D2) task?

**D4** - Check if it's possible to build a triangle from three line segments, if so then which one:
a) equilateral, isosceles, scalene?
b) acute, right, obtuse?

**D5** - Check if given point P(x, y) lays on the triangle described with three coordinates A, B and C.

**D6** - Today is DD/MM/RR. What was the date yesterday? which one is going to be tomorrow?

## List 2

**I1** - Having series of 'n' real numbers, count:
	a) sum of all the numbers,
	b) arithmetic mean of all positive numbers,
	c) arithmetic mean of all the numbers directly following the positive ones.

**I2** - Count the sum of digits of the natural number K. Use division remainder %.

**I3** - How many significant figures does the natural number K have?

**I4** - What is the biggest digit of a natural number K?

**I5** - Check if natural number K is prime.

**I6** - Find the GCD (Greatest Common Divisor) of natural numbers M and N. 

**I7** - Use [Euclidean algorithm](https://en.wikipedia.org/wiki/Euclidean_algorithm)(division remainder version) for the I6 task.

**I8** - Use [Euclidean algorithm](https://en.wikipedia.org/wiki/Euclidean_algorithm)(without division remainder) again for the I6 task.

**I9** - Count the 'X' to the power of 'K' ('K' - natural number). Do your best to minimize muliplications.

**I10** - Using Maclaurin series (special case of [Taylor series](https://en.wikipedia.org/wiki/Taylor_series)) count approximate values of e^x, sin(x) and cos(x) with a given precision.

## List 3

**J1** - Given two dates 'date1' and 'date2' in a format dd/mm/yyyy, make use of correct utilities:

	a) Input and save the date if it's in good format.
	b) Is 'date1' before 'date2'?
	c) Is given year a leaping year?
	d) How many days does the month of a date has?
	e) Find the date 'K' days after the given date.
	f) Find the date 'K' days before the given date.
	g) Find the name of the day of the given date.
	h) Calculate the difference in days between two dates.

**J2** - There is a single argument function (unary function) continuous on range [a, b], that can be computed with method f(x):

	Create iterative algorithms for computing:
	a) Min and max values of a function based on the given range.
	b) Value of function derivative for 'x0' with a given precision (with rigorous definition).
	c) Rounded values of roots of equation f(x) = 0 using bisection method.
	d) Extremums of function in the given range (if there is any), 
		tip: Check the sign of first derivative of f(x).
	e) Fleing points of function f(x) on the given range (assuming there are ones),
		tip: Check the sign of the second derivative of f(x).

## List 4

using as a source of data:
	a) pseudorandom number generator,
	b) console,
write the following programs:

**T1**
	a) Create series as array,
	b) Create set as array,
of random whole numbers in range <1, K> (number of elements: N < K) and then:
	- print array in the order of adding the elements,
	- print the array in the opposite order to order of adding elements,
	- split array into two segregated subsets of even and odd numbers.
Warning: the elements in set cannot repeat.

**T2** - Create two matrices of real numbers with a given size M x N (with M as an input) and then determine:
	a) sum of matrices,
	b) matrix multiplication,
	c) transpose matrices for each given matrix.

**T3** - For a given set 'P' that includes 'n' points on the plane in the XY coordinate system, designate:
	- the furthest point from the beggining of the coordinate system (0, 0),
	- coordinates of the vertices of the square with the smallest possible area and sides parallel to the axis that includes all the points of set 'P',
	- tabel of the distances between all the possible pairs of points,
	- the most distance pair of points,
	- three points that create a triangle with the biggest area,
Moreover:
	- sort set of points in ascending order based on the distance of each points to the beggining of the coordinate system,
	- sort pairs of points in ascending order based on the distances between pairs.

# Basic and external libraries

- openJDK-15,
- Maven: JUnit 4.0 (for List 1 and 2),
- Maven: XChart 3.6.5 (for List 3 - J2),

# Author

- [Jakub Szwedowicz](https://github.com/JakubSzwedowicz)
