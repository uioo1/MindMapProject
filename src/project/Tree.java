package project;

public class Tree {
	Node root;
	Node before_node;
	int before_tab_count, node_count = 0;
	
	public Tree() {
		
	}
	
	int getCharCount(String str, char c) {
        int count = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == c)
                count++;
        }
        return count;
    }
	
	public int getNodeCount() {
		return node_count;
	}
	
	 // �θ� ��忡 �ڽ� ��� �߰�
    public void add(Node parent, Node child) {
        // �θ� ����� �ڽ� ��尡 ���ٸ�
        if(parent.getLeftChild() == null)
            parent.setLeftChild(child);
        // �θ� ����� �ڽ� ��尡 �ִٸ�
        else {
            // �ڽ� ����� ������ ���� �߰�
            Node temp = parent.getLeftChild();
            while(temp.getRightSibling() != null)
                temp = temp.getRightSibling();
             
            temp.setRightSibling(child);
        }
    }
     
    // ��� ���
    public void printTree(Node node, int depth) {
        for(int i = 0; i < depth; i++)
            System.out.print("	");
         
        // ������ ���
        System.out.println(node.getNodeData());
         
        // �ڽ� ��尡 �����Ѵٸ�
        if(node.getLeftChild() != null)
            printTree(node.getLeftChild(), depth + 1);
         
        // ���� ��尡 �����Ѵٸ�
        if(node.getRightSibling() != null)
            printTree(node.getRightSibling(), depth);
    }
	
	public void getTextPanel(String text) {
		Node now_node;
		int now_tab_count = 0;
		String nodes[];		
		nodes = text.split("\\r?\\n");	//���ͷ� ���� �ɰ���
		node_count = nodes.length;
			
		for(int i = 0; i < nodes.length; i++) {
			now_tab_count = getCharCount(nodes[i], '\t');
			now_node = new Node(nodes[i].replaceAll("\t", ""));	//�ǹ��� �������ֱ�
						
			if(i == 0 && now_tab_count == 0) {
				root = now_node;
				root.setParent(null);
				root.setRightSibling(null);
				root.setLeftChild(null);
				before_node = now_node;
				before_tab_count = now_tab_count;
			}
			else if(i != 0 && now_tab_count > before_tab_count) {
				before_node.setLeftChild(now_node);
				now_node.setParent(before_node);
				before_node = now_node;
				before_tab_count = now_tab_count;
			}
			else if(i != 0 && now_tab_count == before_tab_count) {
				before_node.setRightSibling(now_node);
				now_node.setParent(before_node.getParent());
				before_node = now_node;
				before_tab_count = now_tab_count;
			}	
			else if(i != 0 && now_tab_count < before_tab_count) {
				int gap = before_tab_count - now_tab_count;
				
				for(int j = gap; j > 0; j--) {
					before_node = before_node.getParent();
				}
				before_node.setRightSibling(now_node);
				now_node.setParent(before_node.getParent());
				before_node = now_node;
				before_tab_count = now_tab_count;
			}	
			
		}
	}
	
}
