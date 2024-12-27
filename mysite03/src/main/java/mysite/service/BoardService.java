package mysite.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.repository.GuestbookRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	
	public void addContents(int pId, BoardVo vo) {
		boardRepository.insert(pId, vo);
	}
	
	// view
	public BoardVo getContents(int id) {
		return boardRepository.findOne(id);
	}
	
//	public BoardVo getContents(Long id, Long userId) {
//	}
	
	public void updateContents(int pId, BoardVo vo) {
		boardRepository.update(pId, vo);
	}
	
	public void deleteContents(int id, Long userId) {
		boardRepository.deleteById(id);
	}
	
	public List<BoardVo> getContentsPageList(int currentPage){
		return boardRepository.findPageList(currentPage);
	}
	
	public List<BoardVo> getContentsAllList(){
		return boardRepository.findAll();
	}
	
}
