import itertools 
import numpy

def matrice_si_vector_din_fisier(path):
	
	with open(path) as f:

		n = f.readline()
		#print(n)

		vector = []

		for line in itertools.islice(f , 1 , int(n) + 1):
			vector.append(float(line.strip("\n")))

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


def verifica_matrice(matrice, n):
	for i in range(0, n):
		for j in range(0, len(matrice[i])):
			if i == matrice[i][j][1]:
				if matrice[i][j][0] == 0:
					print('Matricea are 0 pe diagonala , nu putem aplica algoritmul')
					return
	print('Matricea nu are nici un 0 pe diagonala')

def get_element_at_position(linie, index):
 
    n = len(linie)
    for i in range(0, n):
        if linie[i][1] == index:
            return linie[i][0]
    return 0

def f3(matrice, vector, xp, n):
	xc = []

	norma = 0
	for i in range(0, n):
		suma = 0
		for k in matrice[i]: #k = cheie => tupla
			if k[1] == i:
				continue
			if k[1] < i:
				suma += get_element_at_position(matrice[i] , k[1]) * xc[k[1]] #matrice[i][k][0] * xc[k[1]]
			else:
				suma += get_element_at_position(matrice[i] , k[1]) * xp[k[1]]   #matrice[i][k][0] * xp[k[1]]
		xi_1 = (vector[i] - suma) / get_element_at_position(matrice[i] , i)
		norma += (xi_1 - xp[i]) ** 2
		xc.append(xi_1)
	return (xc, pow(norma, 1/2))

def gauss_seidel(matrice, vector, epsilon, n):
	kmax = 10000
	#xc pentru vectorul x(k+1) si xp pentru vectorul x(k)
	#Xc = Xp = [i for i in range(1, n+1)]
	k = 0
	deltaX = 0

	xgs = [i for i in range(1, n+1)]

	while True:

		#Xp = Xc
		#Xc = formula3(matrice, vector, Xp, n)

		xgs, deltaX = f3(matrice, vector, xgs, n) 

		#deltaX = distance.euclidean(Xc, Xp)
		k = k + 1
		print('Iteratia a ', k)
		if deltaX < epsilon or k > kmax or deltaX > 100000000:
			break

	if deltaX < epsilon :
		print(xgs ,'este aproximarea cautata a solutiei')
		return xgs
	else:
		print('divergenta')
	print('S-au realizat', k, 'iteratii')


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

def verificare_solutie_afisata(AxGs , b):
	maxim = 0

	for i in range(0,len(AxGs)):
		if(AxGs[i] - b[i] > maxim):
			maxim = abs(AxGs[i] - b[i])

	return maxim

matrice, vector = matrice_si_vector_din_fisier("m_rar_2018_5.txt")
#print(matrice)
n = len(vector)

epsilon = 10 ** -8
verifica_matrice(matrice, n)
xgs = gauss_seidel(matrice, vector, epsilon, n)

print("----------------------------------------------------------------------------------")
print("----------------------------------------------------------------------------------")
print("----------------------------------------------------------------------------------")
print("----------------------------------------------------------------------------------")
print("----------------------------------------------------------------------------------")
print("----------------------------------------------------------------------------------")
print("----------------------------------------------------------------------------------")
AxGs = mul_matrix_with_vector(matrice, xgs)
print("Verificare solutie afisata: ")
norma_infinit = verificare_solutie_afisata(AxGs , vector)
print(norma_infinit)
#print(n)