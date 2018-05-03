import numpy as np 
import itertools 

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


def add_matrix(A, B):

	result = A.copy()
	for i in range(0, len(A)):
		linieA = A[i]
		linieB = B[i]

		for j in range(0, len(linieB)):
			"""
				Vedem daca avem indicele coloanei lui B[i] in linia i a matricii A
				j = (valoare, coloana)
			"""
			found = False

			for k in range(0, len(linieA)):
				if linieA[k][1] == linieB[j][1]:
					"""
						Inseamna ca avem valori pe aceeasi linie si aceeasi coloana a matricelor => facem adunarea
					"""
					result[i][k] = ((result[i][k][0] + linieB[j][0]), linieB[j][1])
					found = True

			if found == False:
				"""
					Daca nu am gasit indice in matricea A => inseram tupla din B pe linia matricii A
				""" 
				result[i].append(linieB[j])

				"""
					Sortam randul dupa valoarea a 2a din fiecare tupla
				"""
				#print(result[i])

	for i in range(0 , len(result)):
		result[i] = sorted(result[i])

	return result


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

def compare_matrixes(A , B , epsilon):
	for i in range(0 , len(A)):
		if(len(A[i]) != len(B[i])):
			print("Nu sunt egale")
			return False
		for j in range(0 , len(B[i])):
			if(abs(A[i][j][0] - B[i][j][0]) > epsilon):
				print("Nu sunt egale")
				return False

	return True

def get_value_at_position(columnB, index):
	for i in range(0, len(columnB)):
		if(columnB[i][1] == index):
			return columnB[i][0]
	return 0


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
 
def get_element_at_position(linie, index):
 
    n = len(linie)
    for i in range(0, n):
        if linie[i][1] == index:
            return linie[i][0]
    return 0
 
 
def mul_two_matrixes(A, B):
    transpusaB = transpusa(B)
 
    rezultat = []
    n = len(A)
   
    for i in range(0, n):
        linie = []
        for j in range(0, n):
            suma = 0
            #print('Aici incepe linia')
            for k in range(0, len(A[i])):
                #print(A[i][k])
                suma += A[i][k][0] * get_element_at_position(transpusaB[j], A[i][k][1])
                #print (A[i][j][0] , get_element_at_position(transpusaB[j], A[i][j][1]) )
            #print(suma)
            if suma != 0:
                linie.append((suma, j))
        if linie != []:
            rezultat.append(linie) 

    for i in range(0 , len(rezultat)):
	    rezultat[i] = sorted(rezultat[i])
 
    return rezultat

def sort_matrix_before_comparing(matrix):
	'''
	Every vector is sorted by first element from tuple
	'''
	for i in range(0 , len(matrix)):
		matrix[i] = sorted(matrix[i])	

def compare_two_vectors(A,B,epsilon):

	for i in range(0 , len(A)):
		if(abs(A[i] - B[i]) > epsilon):
			print("Nu sunt egale")
			return False
	return True

def maxim_10_numere_nenule_pe_linie(matrix):
	for v in range(0,len(matrix)):
		if(len(matrix[v]) > 10):
			return "Exista macar o linie cu mai mult de 10 elemente nenule"
	return "Nu exista nici o linie cu mai mult de 10 elemente nenule"

def maxim_10_numere_nenule_pe_coloana(matrix):
	big_vector = []

	for v in range(0,len(matrix)):
		for i in matrix[v]:
			big_vector.append(i)
	
	dictionar = dict()

	for tup in big_vector:
		if tup[1] in dictionar:
			dictionar[tup[1]] += 1
		else:
			dictionar[tup[1]] = 1

	for k,v in dictionar.items():
		if(v > 10):
			print("Coloana " + str(k) + " are " + str(v) + " elemente nenule")


def problema():

	epsilon = 10**-6

	matriceA , vectorA = matrice_si_vector_din_fisier("a.txt")
	matriceB , vectorB = matrice_si_vector_din_fisier("b.txt")
	matriceAplusB , vectorAplusB = matrice_si_vector_din_fisier("aplusb.txt")
	matriceAoriB , vectorAoriB = matrice_si_vector_din_fisier("aorib.txt")

	#Suma matricilor A si B
	#Rezultatul trebuie sa fie egal cu matricea din fisierul aplusb.txt matriceAplusB
	
	#suma_matricilor_A_si_B = add_matrix(matriceA , matriceB)
	#sort_matrix_before_comparing(matriceAplusB)
	#sort_matrix_before_comparing(suma_matricilor_A_si_B)
	#print(compare_matrixes(matriceAplusB , suma_matricilor_A_si_B , epsilon))
	

	#Inmultirea matricii A cu vectorul [2018,2017,2016 ... 1]
	#Vectorul rezultat trebuie sa fie egal cu vectorul din fisierul a.txt vectorA
	'''
	vector_rezultat = mul_matrix_with_vector(matriceA , [i for i in range(2018,0,-1)])
	print(compare_two_vectors(vector_rezultat , vectorA , epsilon))
	'''

	#Inmultirea matricii A cu matricea B
	#Rezultatul trebuie sa fie egal cu matricea din fisierul aorib.txt matriceAoriB
	
	produsul_matricilor_A_si_B = mul_two_matrixes(matriceA , matriceB)
	#sort_matrix_before_comparing(matriceAoriB)
	#sort_matrix_before_comparing(produsul_matricilor_A_si_B)
	print(matriceAoriB)
	print("--------------------------------------------------------------------------")
	print("--------------------------------------------------------------------------")
	print("--------------------------------------------------------------------------")
	print("--------------------------------------------------------------------------")
	print("--------------------------------------------------------------------------")
	print(produsul_matricilor_A_si_B)
	print(compare_matrixes(matriceAoriB , produsul_matricilor_A_si_B , epsilon))
	#print(maxim_10_numere_nenule_pe_linie(suma_matricilor_A_si_B))
	

problema()