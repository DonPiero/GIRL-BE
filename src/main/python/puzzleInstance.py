from qlearning import model
from scipy.spatial import distance
import numpy as np
import math

class PuzzleInstance(model.Model):

    def __init__(self, initial_state, model, scores):
        self.initial_state = initial_state
        self.scores = scores
        self.model = model

    def readRewards(self, filename):
        f = open(filename, "r")
        for line in f:
            self.rewards.append(list(map(lambda x: int(x), line.strip().split('\t'))))

    def get_no_of_actions(self):
        return 4

    def get_initial_state(self):
        return self.initial_state

    def is_ordered(self, state):
        poz = state.get_poz()
        for i in range(1, poz):
            if state.get_A()[i - 1] > state.get_A()[i]:
                return False
        return True

    def is_ordered_around_poz(self, state):
        poz = state.get_poz()
        if poz == 0:
            return True
        if poz == len(state.get_A()) - 1:
            return True
        if state.get_A()[poz - 1] < state.get_A()[poz + 1]:
            return True
        return False

    def distance(self, state):
        return distance.cityblock(list(filter(lambda num: num != 0, state.get_A())),
                                  np.sort(list(filter(lambda num: num != 0, state.get_A()))))

    def get_reward(self, state, next_state, action):
        if self.model == 0:
            return self.model0(next_state)
        elif self.model == 1:
            return self.model1(next_state)
        elif self.model == 2:
            return self.model2(state, next_state)
        elif self.model == 3:
            return self.model3(next_state)
        elif self.model == 4:
            return self.model4(state, next_state)
        elif self.model == 5:
            return self.model5(next_state)
        elif self.model == 6:
            return self.model6(next_state)
        elif self.model == 7:
            return self.model7(state, next_state, action)
        elif self.model == 8:
            return self.model8(state)
        
    def model0(self, next_state):
        if next_state.is_final():
            return 1000000.0
        else:
            return -1


    def model1(self, next_state):
        if next_state.is_final():
            return 1000000.0
        else:
            return -self.distance(next_state)

    def model2(self, state, next_state):
        if next_state.is_final():
            return 1000000.0
        else:
            v = next_state.get_A()[state.get_poz()]
            if next_state.get_poz() < state.get_poz():
                v = v - 1
            return v - state.get_poz()

    def model3(self, next_state):
        if next_state.is_final():
            return 1000000.0
        else:
            l = list(filter(lambda x: x > 0, next_state.get_A()))
            count = 0
            for i in range(1, len(l)):
                if l[i] != i:
                    count = count + 1
            return -count-self.distance(next_state)

    def model4(self, state, next_state):
        if next_state.is_final():
            return 1000000.0
        else:
            l = list(filter(lambda x: x > 0, next_state.get_A()))
            count = 0
            for i in range(1, len(l)):
                if l[i] != i:
                    count = count + i
            v = next_state.get_A()[state.get_poz()]
            if next_state.get_poz() < state.get_poz():
                v = v - 1            
            return v - state.get_poz() - count

    def model5(self, next_state):
        if next_state.is_final():
            return 1000000.0
        else:
            l = list(filter(lambda x: x > 0, next_state.get_A()))
            n = len(l) % 3 + 1
            count = 0
            for i in range(1, len(l)):
                for j in range(1,len(l)):
                    if (l[i] % n == l[j] % n) and (l[i]<l[j]) and (i>j) :
                        count = count + i
                    if (l[i]//n == l[j]//n) and (l[i]<l[j]) and (i>j) :
                        count = count + i
            return -count-self.distance(next_state)



    def model6(self, next_state):
        if next_state.is_final():
            return 1000000.0
        else:
            return - self.scores[next_state.get_id()]

    def model7(self, state, next_state, action):
        p0 = state.get_poz()
        p1 = next_state.get_poz()
        moved = next_state.get_A()[p0]


        inplace = (p1 // state.n > p0 // state.n) and (p1 // state.n != moved // state.n) and moved < len(state.get_A()) - 1
        if inplace:
            bigger = True
            for after_poz in range(p1+1,len(state.get_A())):
                if state.get_A()[after_poz] < moved and after_poz != p0:
                    bigger = False


        if next_state.is_final():
            return 1000000.0
        elif inplace and bigger:
            # print("State:\n",state,"\nNext state:\n",next_state, p0, p1, moved, next_state.n)
            return 10.0
        else:
            return -1.0

    def model8(self, state):
        if state.is_final():
            return 1000000.0

        total_correct_rows = 0
        total_correct_cols = 0
        diff_rows = 0
        diff_cols = 0

        for piece_poz in range(0,(len(state.get_A()))):
            if piece_poz != state.get_poz():
                piece_val = state.get_A()[piece_poz]
                piece_row = piece_poz // state.n
                piece_col = piece_poz % state.n

                desired_row = (piece_val - 1) // state.n
                desired_col = (piece_val - 1) % state.n

                if piece_row == desired_row:
                    total_correct_rows = total_correct_rows + 1
                else:
                    diff_rows = diff_rows + abs(piece_row - desired_row)

                if piece_col == desired_col:
                    total_correct_cols = total_correct_cols + 1
                else:
                    diff_cols = diff_cols + abs(piece_col - desired_col)
        # print(total_correct_rows, total_correct_cols, diff_rows, diff_cols, (total_correct_rows - diff_rows) + (total_correct_cols - diff_cols))
        return (total_correct_rows - diff_rows) + (total_correct_cols - diff_cols)
