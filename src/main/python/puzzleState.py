from qlearning import state

class PuzzleState(state.State):
    up = 0
    down = 1
    left = 2
    right = 3

    def __init__(self, n, A):
        self.n = n
        self.A = A
        self.l1 = [0,1,3,4]
        self.l2 = [2,5,8]
        self.l3 = [6,7]
        self.score1 = [0,0,0,0]
        self.score2 = [0,0,0]
        self.score3 = [0,0]
        self.visited = {}

    def get_A(self):
        return self.A

    def get_poz(self):
        for i in range(self.n * self.n):
            if self.A[i] == 0:
                return i
        return 0

    def get_possible_actions(self):
        poz = self.get_poz()
        line = poz // self.n
        col = poz % self.n
        actions = []
        if line < self.n - 1:
            actions.append(self.up)
        if line > 0:
            actions.append(self.down)
        if col < self.n - 1:
            actions.append(self.left)
        if col > 0:
            actions.append(self.right)
        return actions

    def is_final(self):
        l = list(filter(lambda x: x > 0, self.A))
        # l = self.A
        for i in range(1, len(l)):
            if l[i-1] > l[i]:
                return False
        return True

    def __swap_copy(self, poz, to_poz):
        newA = self.A[:]
        newA[poz] = self.A[to_poz]
        newA[to_poz] = 0
        return newA

    def get_next(self, action):
        poz = self.get_poz()
        if action == self.up:
            return PuzzleState(self.n, self.__swap_copy(poz, poz + self.n))
        if action == self.down:
            return PuzzleState(self.n, self.__swap_copy(poz, poz - self.n))
        if action == self.left:
            return PuzzleState(self.n, self.__swap_copy(poz, poz + 1))
        if action == self.right:
            return PuzzleState(self.n, self.__swap_copy(poz, poz - 1))

    def get_id(self):
        id = 0
        for e in self.A:
            id = id * 100 + e
        return id

    def __str__(self):
        st = ""
        for i in range(0, self.n):
            for j in range(0, self.n):
                st = st + str(self.A[i*self.n+j])
            st = st + "\n"
        return st


    def updateScore(self, score, poz, l, scl):
        p = 0
        while l[p] != poz:
            p = p + 1
        if score < scl[p] or scl[p] == 0:
            scl[p] = score


    def bfs(self, n):
        Al = list(range(1, n))
        Al.append(0)
        finalstate = PuzzleState(self.n, Al)
        queue = [(finalstate, 0)]
        self.visited = {}
        while len(queue) > 0:
            (current, score) = queue.pop()
            if current.get_id() in self.visited:
                continue
            self.visited[current.get_id()] = score
            actions = current.get_possible_actions()
            for action in actions:
                next_state = current.get_next(action)
                poz = next_state.get_poz()
                if poz in self.l1:
                    score = score + 1
                    self.updateScore(score, poz, self.l1, self.score1)
                if poz in self.l2:
                    score = score + 1
                    self.updateScore(score, poz, self.l2, self.score2)
                if poz in self.l3:
                    score = score + 1
                    self.updateScore(score, poz, self.l3, self.score3)
                queue.append((next_state, score))
        return self.visited

    def merge(self):
        return [self.score1[0], self.score1[1], self.score2[0],
                self.score1[2], self.score1[3], self.score2[1],
                self.score3[0], self.score3[1], self.score2[2]]
