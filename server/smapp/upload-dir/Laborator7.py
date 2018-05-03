# n+1 -> x0, x1, . . ., xn puncte
# n+1 -> valori , ale unei functii f , f(x0) = y0 , f(x1) = y1 ... f(xn) = yn

#sa se aproximeze functia f in xbarat , f(xbarat) pentru un xbarat dat 

def generare_noduri_interpolare_echidistante():

	x_uri = []
	valori_y = []

	f_ = open('y_uri.txt', 'r')

	valori_y = f_.read().split(' ')

	for i in range(0, len(valori_y)):
		valori_y[i] = int(valori_y[i])

	f_ = open('n_x0_xn.txt', 'r')

	n = int(f_.readline())
	x0 = int(f_.readline())
	xn = int(f_.readline())

	h = (xn - x0) / (n - 1)
	print("h = " , h)
	for i in range(x0, n):
		x_uri.append(x0 + i * h)

	new_y = []

	for i in range(0, 6):
		new_y.append(f(i))

	print("newy ",new_y)
	return (h , new_y , x_uri)

def a_si_b_metoda_patrate():
	f = open('a_b_metoda_patrate.txt' , 'r')

	a = int(f.readline())
	b = int(f.readline())

	return (a,b)


def Aitken(valori_y):
	y = valori_y

	aitken_vector = [y[0]]

	for i in range(0, len(valori_y) - 1):
		temp = []
		for j in range(0, len(valori_y) - i - 1):
			temp.append(y[j+1] - y[j])
		aitken_vector.append(temp[0])
		y = temp

	return aitken_vector


#construire functie Sx , folosind polinomu de grad n , polinomul de interpolare Lagrange
def pol_interpolare_Lagrange(h , valori_y, puncte , x_barat):
	print("Polinomul de interpolare Lagrange")

	t = (x_barat - puncte[0]) / h

	s1 = t

	rezultat_schema_aitken = Aitken(valori_y)
	print(rezultat_schema_aitken)

	rez = rezultat_schema_aitken[0] + rezultat_schema_aitken[1] * s1

	for i in range(2, len(rezultat_schema_aitken)):
		s1 = s1 * (t - i + 1) / i
		rez += rezultat_schema_aitken[i] * s1

	print("Ln(x_barat) = " , rez)
	print("|Ln(x_barat) - f(x_barat)| = " , rez - f(x_barat))
	print("\n")

def f(x):
	return (x ** 4) - 12 * (x ** 3) + 30 * (x ** 2) + 12 
	#return (2 * x ** 5) + (x ** 4) - 12 * (x ** 3) + 30 * (x ** 2) + 12 

def horner(A, x):
	b = A[0]
	i = 1
	while i < len(A):
		b = A[i] + b * x
		i += 1
	return b

def interpolare_met_patrate(a , b , x_barat):
	import numpy as np
	print("Interpolare prin metoda celor mai mici patrate")
	#print("Limitele: " , a , b)

	matrice_de_polinoame = []

	for i in range(1,b + 1):
		polinom = []
		for power in range(0,b):
			polinom.append(i ** power)

		matrice_de_polinoame.append(polinom)

	B = np.matrix(matrice_de_polinoame) * a
	print(B)
	puncte = np.array([i for i in range(a , b + 1)])

	pol_f = []

	for i in range(0,len(puncte)):
		pol_f.append(f(puncte[i]))

	print("\n",pol_f)

	print('\nSolutia sistemului folosind libraria numpy este:')
	solutie = np.linalg.solve(B, pol_f)
	solutie = np.flip(solutie , 0)
	for i in range(0, len(solutie)):
		print('a{0} = {1}'.format(i, solutie[i]))

	print("\nHorner: ")
	print("f(x_barat) = " , f(x_barat))
	print("Folosind Horner: " , horner(solutie , x_barat))

x_barat = 1.5

h , valori_y , puncte = generare_noduri_interpolare_echidistante()
pol_interpolare_Lagrange(h , valori_y , puncte , x_barat)


#partea a 2-a 

a , b = a_si_b_metoda_patrate()

interpolare_met_patrate(a , b , x_barat)

