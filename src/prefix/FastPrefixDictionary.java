package prefix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FastPrefixDictionary implements PrefixDictionary {

	private class TrieNode {
		private long value;
		private char c;
		private ArrayList<TrieNode> refs;

		public TrieNode() {
			value = 0;
			c = ' ';
			refs = new ArrayList<TrieNode>();
		}

		public TrieNode(int value, char c) {
			this.c = c;
			this.value = value;
			refs = new ArrayList<TrieNode>();
		}

		public boolean containsKey(Character c) {
			for(int i = 0; i < refs.size(); i++){
				if(c == refs.get(i).c){
					return true;
				}
			}
			return false;
		}

		public TrieNode getNextNode(Character c) {
			for(int i = 0; i < refs.size(); i++){
				if(c == refs.get(i).c){
					return refs.get(i);
				}
			}
			return null;
		}

		public void insertNode(Character c, int value) {
			refs.add(new TrieNode(value, c));
			refs.trimToSize();
		}

		public long value() {
			return value;
		}

		/*public Collection<TrieNode> values() {
			return refs.values();
		}*/
	}

	private class Trie {
		private TrieNode node;

		public Trie() {
			node = new TrieNode();
		}

		public void insert(String word, int value) {
			TrieNode head = node;
			for (int i = 0; i < word.length(); i++) {
				if (head.containsKey(word.charAt(i))) {
					head = head.getNextNode(word.charAt(i));
					head.value += value;
				} else {
					head.insertNode(word.charAt(i), value);
					head = head.getNextNode(word.charAt(i));
				}
			}
			//head.insertNode(word.charAt(word.length() - 1), value);
		}

		public long sum(String prefix) {
			TrieNode head = node;
			for (int i = 0; i < prefix.length(); i++) {
				if (head.containsKey(prefix.charAt(i))) {
					head = head.getNextNode(prefix.charAt(i));
				}else{
					return 0;
				}
			}
			//sum += sumTrav(head);

			return head.value();
		}

	}

	private Trie root;

	public FastPrefixDictionary(String filename) {
		root = new Trie();
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			String line;
			String[] lineList;
			while ((line = file.readLine()) != null) {
				lineList = line.split(",");
				root.insert(lineList[0].trim(),
						Integer.parseInt(lineList[1].trim()));

			}
			// System.out.println(words.get(0).key);
			file.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public long sum(String prefix) {
		return root.sum(prefix);
	}

}