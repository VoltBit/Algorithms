def knapsack():
	objects = [100]
	weights = [100]
	# dp = [][]
	fileStream = open('data.in', 'r')
	objectCount = int(fileStream.read(1))
	fileStream.read(1)
	weightsCount = int(fileStream.read(1))
	for i in range(objectCount):
		objects[i] = fileStream.read(1)
		fileStream.read(1)
		weights[i] = fileStream.read(1)
	fileStream.close()
	for i in range(weightsCount):
		dp[0][i] = 0
	for i in range(objectCount):
		for j in range(weightsCount):
			if j >= weights[i]:
				dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - weights[i]])
			else:
				dp[i][j] = dp[i - 1][j]


knapsack()