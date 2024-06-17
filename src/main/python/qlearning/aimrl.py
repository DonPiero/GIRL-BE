import matplotlib.pyplot as plt
import os
import random
import json


def qlearning(instance, no_of_epochs, epsilon, alpha, discount, decay, limit, optimisation, verbose=False, discount_optimisation=True):
    no_of_actions = instance.get_no_of_actions()
    Q = {} # state |-> [act0, act1, ...]
    C = {} # state |-> counter
    global_counter = 0
    results = {} # epoch |-> no_of_steps_until_solution_found
    solutions = {} # epoch |-> flag which indicates if solution reached
    min_solution = no_of_epochs
    rng = random.SystemRandom()
    for epoch in range(0,no_of_epochs):
        counter = 1
        state = instance.get_initial_state()
        for counter in range(limit):
            if state.is_final() > 0:
                if state.is_final() == 1:
                    solutions[epoch] = True
                    if min_solution > counter:
                        min_solution = counter
                break

            if not (state.get_id() in Q.keys()):
                Q[state.get_id()] = [0.0] * no_of_actions

            v = rng.random()
            actions = state.get_possible_actions()
            if not actions:
                break
            if v <= epsilon:
                action = rng.choice(actions)
            else:
                q_values = Q[state.get_id()]
                max_value = q_values[actions[0]] # actions is not empty
                max_actions = [actions[0]]
                for a in actions:
                    if q_values[a] > max_value:
                        max_value = q_values[a]
                        max_actions = [a]
                    elif actions[0] != a and q_values[a] == max_value:
                        max_actions.append(a)
                action = rng.choice(max_actions)

            next_state = state.get_next(action)
            next_max = max(Q[next_state.get_id()]) if next_state in Q.keys() else 0.0
            reward = instance.get_reward(state, next_state, action)
            old_value = Q[state.get_id()][action]
            value = (1 - alpha) * old_value + alpha * (reward + discount * next_max)
            Q[state.get_id()][action] = value
            global_counter = global_counter + value*len(Q)
            C[state.get_id()] = global_counter
            if discount_optimisation:
                discount = discount_linear_decrease(discount, value)
            
            state = next_state
            counter = counter + 1
        if epsilon >= 0.1:
            epsilon = epsilon * decay

        if optimisation == 1:
            ks = [i for i in C if C[i] < global_counter - min_solution*len(Q)]
        if optimisation == 2:
            ks = [i for i in C if C[i] < global_counter - min_solution]
        if optimisation == 3:
            ks = [i for i in C if C[i] < len(Q) - limit]
        if optimisation != 0:
            for s in ks:
                del Q[s]
                del C[s]
        if verbose:
            print(str(epoch) + "\t" + str(counter)) 
        results[epoch] = counter
    return Q, results, solutions, len(list(solutions.keys())) / no_of_epochs

def discount_linear_decrease(discount, value):
    delta = 1.0
    if discount - value <= delta:
        return 1 / 2 * ((discount - value) * (discount - value));
    else:
        return delta * (abs(discount - value) - 1 / 2 * delta);

def qlearning_from_config(instance, json_config_file, verbose=False, discount_optimisation=True):
    f = open(json_config_file)
    data = json.load(f)
    epsilon = data["epsilon"]
    alpha = data["alpha"]
    discount = data["discount"]
    decay = data["decay"]
    no_of_epochs = data["no_of_epochs"]
    limit = data["limit"]
    return qlearning(instance, no_of_epochs, epsilon, alpha, discount, decay, limit, verbose,discount_optimisation)


def save_as_graph(results, solutions, percentage, no_of_epochs, dirpath, fname, figwidth=16, figheight=12,x_label='Epoch',y_label='No of steps until solution reached'):
    max_h = -1
    s1 = []
    t1 = []
    s2 = []
    t2 = []
    for i in range(no_of_epochs):
        s1.append(i)
        t1.append(results[i])
        if i in solutions.keys():
            s2.append(i)
            t2.append(results[i])
        if max_h < results[i]:
            max_h = results[i]
    fig, ax = plt.subplots()
    ax.plot(s2, t2, color='g', linewidth=3)
    ax.scatter(s2, t2, color='b', linewidth=5)
    ax.plot(s1, t1, color='#A9A9A9')
    ax.text(no_of_epochs - 33, max_h + 15,'Solutions:' + str(round(percentage * 100, 2)) + "%" , fontsize=20, color="b")
    ax.set(xlabel=x_label, ylabel=y_label, title=fname)
    ax.grid()
    fig.set_figwidth(figwidth)
    fig.set_figheight(figheight)
    fig.savefig(dirpath + os.sep + fname)
    plt.close()