import random
from math import sqrt, exp
import numpy as np

def horner(A, x):
	b = A[0]
	i = 1
	while i < len(A):
		b = A[i] + b * x
		i += 1
	return b

def gaseste_R(coeficienti):
	return (abs(coeficienti[0]) + max([abs(i) for i in coeficienti[1:]])) / abs(coeficienti[0])

def metoda_muller(coeficienti, x0, x1, x2, epsilon = 1e-12):
	k = 3
	kmax = 10000
	while True:
		h0 = x1 - x0
		h1 = x2 - x1

		delta0 = (horner(coeficienti, x1) - horner(coeficienti, x0)) / h0
		delta1 = (horner(coeficienti, x2) - horner(coeficienti, x1)) / h1

		a = (delta1 - delta0) / (h1 + h0)
		b = a * h1 + delta1
		c = horner(coeficienti, x2)
	
		if ( b ** 2 - 4 * a * c < 0):
			break 

		if abs(max((b + sqrt(b * b - 4 * a * c)), (b - sqrt(b * b - 4 * a * c)))) < epsilon:
			#print("STOP")
			break

		deltaX = (2 * c) / max((b + sqrt(b * b - 4 * a * c)), (b - sqrt(b * b - 4 * a * c)))
		x3 = x2 - deltaX
		k = k + 1

		x0 = x1 
		x1 = x2
		x2 = x3

		if(abs(deltaX) < epsilon):
			return x3
		if k > kmax or deltaX > 10 ** 8:
			break
	return 'divergenta'



def derivata1(coeficienti, x, h = 10 ** -5):
	return (3 * horner(coeficienti, x) - 4 * horner(coeficienti, x - h) + horner(coeficienti, x - 2*h)) / 2 * h

def derivata2(coeficienti, x, h = 10 ** -5):
	return (-1 * horner(coeficienti, x + 2 * h) + 8 * horner(coeficienti, x + h) - 8 * horner(coeficienti, x - h) + horner(coeficienti, x - 2 * h)) / 12 * h 

def derivata_secunda(coeficienti, x, h = 10 ** -5):
	return (-1 * horner(coeficienti, x + 2 * h) + 16 * horner(coeficienti, x + h) - 30 * horner(coeficienti, x) + 16 * horner(coeficienti, x - h) - 1 * horner(coeficienti, x - 2 * h)) / 12 * h ** 2

def metoda_secantei(coeficienti, x0, x1):
	k = 0

	deltaX = None
	epsilon = 10 ** -5

	k = 0
	kmax = 10000

	derivata = derivata2
	while True:

		if ( -1 * epsilon <= derivata(coeficienti, x1) - derivata(coeficienti, x0) <= -1*epsilon ):
			deltaX = 10 ** -5
		else:
			deltaX = (x1 - x0) * derivata(coeficienti, x1) / (derivata(coeficienti, x1) - derivata(coeficienti, x0))
		
		x2 = x1 - deltaX

		x0 = x1
		x1 = x2

		k += 1
		if abs(deltaX) < epsilon:
			return x2
		if abs(deltaX) < epsilon or k > kmax or abs(deltaX) > 10 ** 8:
			break
	print('divergenta')
	return 'divergenta'

#coeficienti = [1, -2, 1]

#R = gaseste_R(coeficienti)





#metoda_secantei(coeficienti, 1, 2)

def adauga_solutie(solutii, valoare, epsilon = 1e-12):
	if(len(solutii) == 0):
		solutii.append(valoare)
		return

	count = 0
	for i in solutii:
		if abs(i - valoare) > abs(epsilon):
			count += 1
	if count >= len(solutii):
		solutii.append(valoare)


# #coeficienti = [1, -6, 11, -6]
# coeficienti = [42, -55, -42, 49, -6]
# R = gaseste_R(coeficienti)
# start = -R

# solutii = []

# while start <= R:
# 	x0 = start + 0.0001
# 	x1 = start + 0.0002
# 	x2 = start + 0.0003

# 	rez = metoda_muller(coeficienti, x0, x1, x2)
# 	if rez != 'divergenta':
# 		adauga_solutie(solutii, rez)
		
# 	rez = metoda_muller(coeficienti, x0, x2, x1)
# 	if rez != 'divergenta':
# 		adauga_solutie(solutii, rez)

# 	rez = metoda_muller(coeficienti, x1, x0, x2)
# 	if rez != 'divergenta':
# 		adauga_solutie(solutii, rez)

# 	rez = metoda_muller(coeficienti, x1, x2, x0)
# 	if rez != 'divergenta':
# 		adauga_solutie(solutii, rez)

# 	rez = metoda_muller(coeficienti, x2, x1, x0)
# 	if rez != 'divergenta':
# 		adauga_solutie(solutii, rez)

# 	rez = metoda_muller(coeficienti, x2, x0, x1)
# 	if rez != 'divergenta':
# 		adauga_solutie(solutii, rez)

# 	start += 0.0003

# print('Radacini ale polinomului', solutii)

# with open('rezultate.txt', 'w') as f:
# 	f.write(str(solutii))



#coeficienti = [1, -4, 3]
coeficienti = [1, -4, 3]
rez = metoda_secantei(coeficienti, 1, -1)

if rez != 'divergenta':
	print('Minimul este:', rez)
	print('Verificarea solutiei F"(x) =', derivata_secunda(coeficienti, rez) )