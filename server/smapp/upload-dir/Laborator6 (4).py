"""
	p > 0
	n 
	A - matrice

	p >= n
	epsilon
	A p x n
	b p dimensiuni
"""

import random
import time
import itertools


def problema1(p):
	n = p

	matrice = []
	
	for i in range(0, n):
		matrice.append({})

	#pentru fiecare linie generam un numar random de elemente intre 0 si 10
	for i in range(0, n):
		nr_elemente = random.randint(1, 5)
		
		numere_linie = random.sample(range(1,100), nr_elemente)
		pozitii_coloane = sorted(random.sample(range(0, n), nr_elemente))

		for j in range(0, nr_elemente):
			#tupla = (numere_linie[j], pozitii_coloane[j])
			matrice[i][pozitii_coloane[j]] = numere_linie[j]
			matrice[pozitii_coloane[j]][i] = numere_linie[j]

	matrice_rezultat = []

	for i in range(0, len(matrice)):
		linie = []
		for j in matrice[i]:
			linie.append((matrice[i][j], j))
		matrice_rezultat.append(linie)
	return matrice_rezultat


def transpusa(matrice):
    transpusa = []
    n = len(matrice)
    for i in range(0, n):
        column = []
        for j in range(0, n):
            for k in range(0, len(matrice[j])):
                if matrice[j][k][1] == i:
                    column.append((matrice[j][k][0], j))
                    break
        transpusa.append(column)
    return transpusa

def matrice_si_vector_din_fisier(path):
	
	with open(path) as f:

		n = f.readline()
		#print(n)

		vector = []

		f.readline()

		dic = dict()

		for line in f:
			#print(line.strip("\n").split(","))
			value = float(line.strip("\n").split(",")[0])
			i = int(line.strip("\n").split(",")[1])
			j = int(line.strip("\n").split(",")[2])
			
			dic.setdefault(i  , [])
			dic[i].append((value , j))
				

		
		matrice = []
		for v in sorted(dic):
			linie_in_matrice = []
			for i in dic[v]:
				linie_in_matrice.append(i)

			matrice.append(linie_in_matrice)

		#print(matrice)
		return (matrice , vector)


def compare_matrixes(A , B , epsilon):
	for i in range(0 , len(A)):
		if(len(A[i]) != len(B[i])):
			print("Nu sunt egale")
			return False
		for j in range(0 , len(B[i])):
			if(abs(A[i][j][0] - B[i][j][0]) > 0.0010):
				print("Nu sunt egale")
				return False

	return True

def sort_matrix(matrix):
	for i in range(0, len(matrix)):
		matrix[i] = sorted(matrix[i])

def verifica_data_e_simetrica(A):
	n = len(A)
	B = transpusa(A)

	sort_matrix(A)
	sort_matrix(B)

	if compare_matrixes(A, B, 1) == True:
		print('Matricea e simetrica')
	else:
		print('Matricea nu e simetrica')



def mul_matrix_with_vector(A, vector):

	ret = []

	result = A.copy()

	for i in range(0, len(A)):
		suma = 0
		for j in range(0, len(A[i])):
			suma += (A[i][j][0] * vector[A[i][j][1]])


		ret.append(suma)
		suma = 0

	return ret


def norma(vector):
	return pow(sum([i * i for i in vector]), 1/2)

def produs_scalar(A, B):
	"""produs = 0
				for i in range(0, len(A)):
					produs += A[i] * B[i]
			
				return produs"""
	return sum([i*j for (i, j) in zip(A, B)])


def v0(n):
    x = random.sample(range(1000*n), n)
    norma2 = norma(x)

    x = [float(elem)/norma2 for elem in x]

    return x


def metoda_puterii(A, n, epsilon = pow(10, -12)):
    v = v0(n)
    w = mul_matrix_with_vector(A, v)
    k = 0
    kmax = 10 ** 5
    gamma = produs_scalar(w, v)
    while(1):
        norma2 = norma(w)
        v = [elem/norma2 for elem in w]
        w = mul_matrix_with_vector(A, v)
        #print k," . ",gamma

        gamma = produs_scalar(w, v)
        k += 1
        if norma([x1 - gamma * x2 for (x1, x2) in zip(w, v)])<= (n * epsilon) or k > kmax:
            break

    if k > kmax:
        return None,""
    else:
        return gamma, v


def ultima_parte(p, n):
	import numpy as np
	matrice = np.random.rand(p, n)
	U, s, V = np.linalg.svd(matrice, full_matrices = True)
	S = np.diag(s)
	if p > n:
		for i in range(abs(n-p)):
			S = np.vstack([S, [0]*n])

	if p < n:
		print([0] * p)
		X0 = np.zeros((p, 1))
		for i in range(abs(n-p)):
			S = np.hstack((S, X0))

	rang = len([i for i in s if i != 0])

	r = [i for i in s if i != 0]
	print('Rangul matricei A este:', len([i for i in s if i != 0]))
	print('Numarul de conditionare al matricei A este:', max(s) / min([i for i in s if i > 0]))

	r = [1/i for i in s if i > 0]
	print(S)
	S_I = np.diag(r)

	if p > len(r):
		_n, _m = matrice.shape
		X0 = np.zeros((_m, 1))
		for i in range(abs(p - len(r))):
			S_I = np.hstack((S_I, X0))
	
	if n > len(r):
		_n, _m = matrice.shape
		X0 = np.zeros((_n, 1))
		for i in range(abs(n - len(r))):
			S_I = np.vstack((S_I, [0] * _n))

	print('Pseudoinversa Moore-Penrose este: ')
	print(np.dot(np.dot(V, S_I), U.transpose()))

	b = np.random.rand(p)
	print('Vectorul x este:', np.dot(np.dot(np.dot(V, S_I), U.transpose()), b))

	s = random.randint(1, rang)
	print('s = ', s)
	A_S = np.zeros((n, n))
	for i in range(1, s+1):
		_u = U[:i][0][:n]
		_v = V[:i][0][:n]

	
		A_S = np.add(r[i-1]*np.column_stack(_u)*np.column_stack(_v).transpose(), A_S)
	
	print('AS este :')	
	print(A_S)


lines = [line.rstrip('\n') for line in open('tema6.txt')]

p = int(lines[0])
n = int(lines[1])
epsilon = float(lines[2])

if (p == n and p > 500):
	matrice_rezultat = problema1(p)
	print(matrice_rezultat)
	verifica_data_e_simetrica(matrice_rezultat)
elif p == n:
	"""A = problema1(p)
	gamma, v = metoda_puterii(A, n, epsilon)
	print('Valoarea proprie de modul maxim este', gamma)
	print(v)"""
	matrice, vector = matrice_si_vector_din_fisier('m_rar_sim_2018.txt')
	verifica_data_e_simetrica(matrice)
	gamma, v = metoda_puterii(matrice, 1500, epsilon)
	print('Valoarea proprie de modul maxim pentru matricea din fisier este:', gamma)
	print(v)

	# A = problema1(p)
	# verifica_data_e_simetrica(A)

	# gamma, v = metoda_puterii(A, n, epsilon)
	# print('Valoarea proprie de modul maxim pentru matricea generata random este:', gamma)
	# print(v)

	# gamma, v = metoda_puterii(matrice, n, epsilon)
	# print('Valoarea proprie de modul maxim pentru matricea din fisier este:', gamma)
	# print(v)

elif p > n:
	ultima_parte(p, n)

# n = 5000

# matrice = problema1(n)

# #verifica_data_e_simetrica(matrice)
# gamma, v = metoda_puterii(matrice, n)

# print(gamma, v)