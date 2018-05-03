import numpy



def problema1():
	u = 1 / 10

	while 1 + u != 1:
		u /= 10
	return u

print('Precizia masina este ', problema1())


def problema2(u):
	x = 1.0
	y = u
	z = u

	if (x + y) + z != x + (y + z):
		print('Operatia de adunare nu este asociativa')
	else:
		print('Operatia de adunare este asociativa')

	x = 1.0000000000000001e-4
	y = 1.0000000000000001e-2
	z = 1.0000000000000001e-3
	if (x * y) * z != x * (y * z):
		print('Operatia de inmultire nu este asociativa pentru x = ', x , ' y = ', y , ' z = ', z)
	else:
		print('Operatia de inmultire este asociativa pentru x = ', x , ' y = ', y , ' z = ', z)

problema2(problema1())


import numpy as np

def multiply_Strassen(A, B, n, n_min):
	
	if n <= n_min:
		
		return np.dot(A, B)

	else:	
		#Impartim matricea A in 4 
		A_upper_half = np.hsplit(np.vsplit(A, 2)[0], 2)
		A_lower_half = np.hsplit(np.vsplit(A, 2)[1], 2)

		#Impartim matricea B in 4
		B_upper_half = np.hsplit(np.vsplit(B, 2)[0], 2)
		B_lower_half = np.hsplit(np.vsplit(B, 2)[1], 2)
		
		A11 = A_upper_half[0]
		A12 = A_upper_half[1]
		A21 = A_lower_half[0]
		A22 = A_lower_half[1]

		B11 = B_upper_half[0]
		B12 = B_upper_half[1]
		B21 = B_lower_half[0]
		B22 = B_lower_half[1]
		# P1 = ( A11 + A22 ) ( B11 + B22 )
		P1 = multiply_Strassen(np.add(A11, A22), np.add(B11, B22), n/2, n_min)
		# P2 = ( A21 + A22 ) B11
		P2 = multiply_Strassen(np.add(A21, A22), B11, n/2, n_min)
		# P3 = A11 (B12 - B22)
		P3 = multiply_Strassen(A11, np.subtract(B12, B22), n/2, n_min)
		# P4 = A22 ( B21 - B11)
		P4 = multiply_Strassen(A22, np.subtract(B21, B11), n/2, n_min)
		# P5 = (A11 + A12 ) B22
		P5 = multiply_Strassen(np.add(A11, A12), B22, n/2, n_min)
		# P6 = (A21 - A11) (B11 + B12)
		P6 = multiply_Strassen(np.subtract(A21, A11), np.add(B11, B12), n/2, n_min)
		# P7 = (A12 - A22) (B21 + B22)
		P7 = multiply_Strassen(np.subtract(A12, A22), np.add(B21, B22), n/2, n_min)

		# C11 = P1 + P4 - P5 + P7
		C11 = np.add(np.subtract(np.add(P1, P4), P5), P7)
		# C12 = P3 + P5
		C12 = np.add(P3, P5)
		# C21 = P2 + P4
		C21 = np.add(P2, P4)
		# C22 = P1 + P2 - P2 + P6
		C22 = np.add(np.subtract(np.add(P1, P3), P2), P6)

		#grupam rezultatele intr-o singura matrice
		C=np.vstack([np.hstack([C11, C12]), np.hstack([C21, C22])])
		return C

A = numpy.matrix('-1 14.4 17 8; 3 -0.04 18 78; -8 1.07 0.171 0.12; 14 -4 1.5 18')
B = numpy.matrix('1 2 3 4; 5 6 7 8; 9 10 11 12; 10 14 15 16')

print(multiply_Strassen(A, B, 4, 2))

print(np.dot(A, B))

