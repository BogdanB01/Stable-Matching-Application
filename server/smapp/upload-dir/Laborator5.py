import numpy as np

"""

	TEMA 5 
	n - dimensiunea matricei, epsilon - precizia calculelor, kmax - numarul maxim de iteratii 
	A - matrice patratica si nesingulara n x n 
"""

"""
	1 - aproximati inversa lui A (V0 cu 5 si 6)
"""

def norma_1(A):
	n = len(A)
	max = 0
	for j in range(0, n):
		suma = 0
		for i in range(0, n):
			suma += A[i][j]
		if suma > max:
			max = suma
	return max
			
def norma_infinit(A):
	n = len(A)
	max = 0
	for j in range(0, n):
		suma = 0
		for i in range(0, n):
			suma += A[j][i]
		if suma > max:
			max = suma
	return max

def calculeaza_v0(A):
	a_transpus = np.matrix.transpose(A)
	norm = norma_1(A)
	infnorm = norma_infinit(A)
	return np.divide(a_transpus, norm * infnorm)


def formula_1(A, Vk):
	return np.dot(Vk, np.subtract(2 * np.identity(len(A)), np.dot(A, Vk)))

def formula_2(A, Vk):
	return np.dot(Vk, np.subtract(3*np.identity(len(A)) ,np.dot(np.dot(A, Vk), np.subtract(3 * np.identity(len(A)), np.dot(A, Vk)))))

def formula_3(A, Vk):
	n = len(A)
	t1 = 1/4 * np.subtract(np.identity(n), np.dot(Vk, A))
	t2 = np.subtract(3 * np.identity(n), np.dot(Vk, A))
	t2_patrat = np.dot(t2, t2)
	return np.dot(np.add(np.identity(n), np.dot(t1, t2_patrat)), Vk)

def inversa_iterativ(A, epsilon):
	V0 = V1 = calculeaza_v0(A)
	k = 0
	kmax = 10000 #de exemplu

	deltaV = None
	while True:
		
		V0 = V1
		V1 = formula_1(A, V0)
		#V1 = formula_2(A, V0)
		#V1 = formula_3(A, V0)

		deltaV = np.linalg.norm(V1 - V0)

		k = k + 1
		if deltaV < epsilon or k > kmax or deltaV > 10 ** 10:
			break

	if deltaV < epsilon:
		print(V1)
		#for i in V1:
		#	print(i)
		print('Este aproximarea cautata a solutiei')
		print('Norma este :', norma_1(np.subtract(np.dot(A, V1), np.identity(len(A)))))
	else:
		print('Divergenta') 
	
	print('S-au efectuat', k ,'iteratii')

def generare_matrice(n):
	A = np.identity(n)

	for i in range(0, len(A) - 1):
		A[i, i+1] = 4
	return A



def problema_3():
	for i in range(5, 10):
		inversa_iterativ(generare_matrice(i), 0.0000000001)		

	print('Forma generala a inversei matricii matricii este:')
	print('1 , (-1) ** indice coloana * 4 ** 1 ........(-1) ** indice coloana * 4 ** (n-1)')
	print('0 , 1                                       ,(-1) ** indice coloana * 4 ** (n-2)')

problema_3()
#A = np.loadtxt('tema5.txt', dtype = 'f')
#inversa_iterativ(A, 0.00000000001)

