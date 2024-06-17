from qlearning import aimrl as QL
import os
import shutil
import json
import sys
import time 
import math
import random
import string

from puzzleInstance import PuzzleInstance
from puzzleState import PuzzleState

def create_if_not_exists(folder):
    cwd = os.getcwd()
    dirpath = os.path.join(cwd, folder)
    if not (os.path.exists(dirpath) and os.path.isdir(dirpath)):
        os.makedirs(dirpath)

def run(folder, fname, instance, no_of_epochs, epsilon, alpha, discount, decay, limit, optimisation, generate_graph=True):
    Q, results, solutions, percentage = QL.qlearning(instance, no_of_epochs, epsilon, alpha, discount, decay, limit, optimisation, True, True)
    if generate_graph:
        QL.save_as_graph(results, solutions, percentage, no_of_epochs, folder, fname)
    return Q, results, solutions, percentage


def save_line(png_file, model, instance, epsilon, decay, alpha, discount, run, time_elapsed, percentage, Q_size, csv_stats, sep="\t"):
    with open(csv_stats, 'a+') as f:
        to_write = (str(png_file) + sep + 
                    str(model) + sep + 
                    str(instance) + sep + 
                    str(epsilon) + sep + 
                    str(decay) + sep + 
                    str(alpha) + sep + 
                    str(discount) + sep + 
                    str(run) + sep + 
                    str(time_elapsed) + sep + 
                    str(percentage) + sep + 
                    str(Q_size))
        f.write(to_write + "\n")

def save_q_table(qtbl, name, folder):
    loc = folder + os.sep + name
    with open(loc, 'w') as f:
        for k in qtbl:
            f.write("\t".join(map(lambda x: str(x), qtbl[k])) + "\n")
    f.close()

def main():

    # init config
    config_file = sys.argv[1]
    if len(sys.argv) >= 3:
        qtablename = sys.argv[2]
    else:
        qtablename = 'qtbl.csv'
    f = open(config_file)
    data = json.load(f)

    # params
    limit = data["limit"]
    epsilon_l = data["epsilon"]
    alpha_l = data["alpha"]
    discount_l = data["discount"]
    decay_l = data["decay"]
    no_of_epochs = data["no_of_epochs"]
    runs = data["runs"]
    instances = data["instances"]
    models = data["model"]
    generate_graph = data["generate-graph"]
    optimisation = int(data["optimisation"])
    
    # setup results file
    csv_stats = config_file + ".csv"
    if os.path.exists(csv_stats):
        os.remove(csv_stats)
    save_line("graph", "model", "instance", "epsilon", "decay", "alpha", "discount", "run", "time", "percentage", "Q size", csv_stats)
    folder = os.path.splitext(config_file)[0]
    if os.path.exists(folder) and os.path.isdir(folder):
        shutil.rmtree(folder)
    create_if_not_exists(folder)

    for inst in instances:
        n = round(math.sqrt(len(inst)))
        state = PuzzleState(n, inst)
        scores = state.bfs(len(inst))
        for model in models:
            instance = PuzzleInstance(state, model, scores)
            for epsilon in epsilon_l:
                for alpha in alpha_l:
                    for discount in discount_l:
                        for decay in decay_l:
                            for i in range(runs):
                                png_file = ''.join(random.choices(string.ascii_lowercase, k = 15))
                                time_elapsed = time.time()
                                Q, results, solutions, percentage = run(folder, png_file, instance, no_of_epochs, epsilon, alpha, discount, decay, limit, optimisation, generate_graph)
                                time_elapsed = round((time.time() - time_elapsed) * 1000, 2)
                                percentage = round((percentage * 100), 2)
                                len_Q = len(Q)
                                save_line(png_file, model, inst, epsilon, decay, alpha, discount, i, time_elapsed, percentage, len_Q, csv_stats) 
                                qprefix = "".join(map(str, inst))
                                save_q_table(Q, qprefix + qtablename, folder)
                        
if __name__ == "__main__":
    main()