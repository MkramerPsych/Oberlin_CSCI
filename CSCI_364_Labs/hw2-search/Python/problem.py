"""
The Problem class represents the problem model used by the agent
to find a path from the start state to a goal state in a given Map

Adam Eck
02/20/2019
"""

import map

class Problem():
    """
    Constructs a new Problem from a given Map

    map: the Map underlying the Problem
    """
    def __init__(self, map):
        # save the params
        self.map = map

    """
    Converts a MapNode to a state representation
    """
    def convertToState(self, node):
        state = (node.lat, node.long)
        return state

    """
    Creates the start state for the problem
    """
    def startState(self):
        return self.convertToState(self.map.startNode)

    """
    Finds the set of all possible actions from a state.

    state: the state to find actions for
    """
    def actions(self, state):
        actionList = []
        node = self.map.nodeMap[state]
        for neighbor in node.neighbors:
            actionList.append(self.convertToState(neighbor))

        return actionList

    """
    Determines the next state after taking an action in a state

    state: the state the action was taken in
    action: the action taken
    """
    def result(self, state, action):
        nextNode = self.map.nodeMap[action]
        return self.convertToState(nextNode)

    """
    Determines the cost of taking an action in a state

    state: the state the action was taken in
    action: the action taken
    """
    def cost(self, state, action):
        node = self.map.nodeMap[state]
        neighbor = self.map.nodeMap[action]
        return node.costs[neighbor]

    """
    Determines whether or not a state is a goal state

    state: the state to check whether it is a goal
    """
    def goal(self, state):
        node = self.map.nodeMap[state]
        return node in self.map.goalNodes

    """
    Returns the list of goal states in the environment
    """
    def goalStates(self):
        goals = []
        for node in self.map.goalNodes:
            goals.append(self.convertToState(node))
        return goals
