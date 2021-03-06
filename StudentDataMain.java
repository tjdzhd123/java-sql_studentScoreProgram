package com.mire.score;
//소스 여닫이 ctrl + shift + 오른쪽 키패드 위 "/"
//ctrl + *
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentDataMain {
   public static Scanner scan = new Scanner(System.in);
   public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, PRINT = 5, SORT = 6, EXIT = 7;
   public static final int NAME = 1, BIRTHDAY = 2;
   public static void main(String[] args) {
      
      boolean flag = false;
      while(!flag) {
         int no = selectMenu(); //메뉴선택
         switch(no) {
         case INSERT: insertStudent(); break; //삽입
         case SEARCH: searchStudent(); break; //검색
         case DELETE: deleteStudent(); break; //삭제
         case UPDATE: updateStudent(); break; //수정
         case PRINT:  printStudent(); break;  //출력
         case SORT:     sortStudent(); break; //정렬
         case EXIT: System.out.println("프로그램 종료"); //종료
                  scan.nextLine(); flag = true; break;
         default : System.out.println("다시 입력해주세요"); break;
         }
      }
   }
   //입력 : 반 번호, 이름, 성별, 생년월일, 국어, 수학, 영어 를 입력한다.
   private static void insertStudent() {
	  //입력 받아야할 것
      int cnum = 0;
      String name = null;
      String gender = null;
      String birthday = null;
      int kor = 0;
      int math = 0;
      int eng = 0;
      
      //반 번호
      while(true) {
         System.out.print("반 번호 입력(30xx) >>");
         cnum = scan.nextInt();
         
         if(cnum >= 3000 && cnum <= 3099) {
            break;
         }else {
            System.out.println("다시 입력해주세요.");
         }
      }
      //이름
      while(true) {
         System.out.print("이름 입력 >>");
         scan.nextLine();
         name = scan.nextLine();
         
         if(patternCheck(name, NAME)) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }
      //성별
      while(true) {
         System.out.print("성별(남자 or 여자) 입력 >>");
         gender = scan.nextLine();
         
         if(gender.equals("남자") || gender.equals("여자")) {
            break;
         }else {
            System.out.println("다시 입력해주세요");
         }
      }
      
      //생년월일
      while(true) {
         System.out.print("생년월일(1995-03-09)10자 입력>>");
         birthday = scan.nextLine();
         
         if(patternCheck(birthday, BIRTHDAY)) {
            break;
         }else {
            System.out.println("다시 입력해주세요.");
         }
      }
      //국어
      while(true) {
         System.out.print("국어 점수(0~100) 입력>>");
         kor = scan.nextInt();
         
         if(kor >= 0 && kor <= 100) {
            break;
         }else {
            System.out.println("다시 입력해주세요.");
         }
      }
      //수학
      while(true) {
         System.out.print("수학 점수(0~100) 입력>>");
         math = scan.nextInt();
         
         if(math >= 0 && math <= 100) {
            break;
         }else {
            System.out.println("다시 입력해주세요.");
         }
      }
      //영어
      while(true) {
         System.out.print("영어 점수(0~100) 입력>>");
         eng = scan.nextInt();
         
         if(eng >= 0 && eng <= 100) {
            break;
         }else {
            System.out.println("다시 입력해주세요.");
         }
      }
      scan.nextLine();
      //삽입할 모델 생성
      StudentDataSub studentDataSub = new StudentDataSub( cnum, name, gender, birthday, kor, math, eng);
      //메소드선언한 총점 평균 등급을 사용하기
      studentDataSub.totalScore();
      studentDataSub.avrScore();
      studentDataSub.gradeScore();
      
      int returnvalue = DBController.StudentInsert(studentDataSub);
      
      if(returnvalue != 0) {
    	  System.out.println(studentDataSub.getName()+"님 삽입 성공");
      }else {
    	  System.out.println(studentDataSub.getName()+"님 삽입 실패");
      }
      
   }
   
   //검색 (이름, 성별)
   private static void searchStudent() {
	   final int NAME_NUM = 1, GENDER_NUM = 2, EXIT = 3;
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      //검색할 내용을 요청(이름과 성별)
      String name = null;
      String gender = null;
      
      String searchData = null;
      int number = 0;
      int no = 0;
      boolean flag = false;      
      
      //검색 메뉴
      no = studentMenu();
      
      switch(no) {
      case NAME_NUM :
    	  while(true) {
				System.out.print("찾고자 하는 이름 입력>>");
				name = scan.nextLine();
				
				if(patternCheck(name, NAME)) {
					break;
				}else {
					System.out.println("다시 입력해주세요.");
				}
    	  }
			searchData = name;
			number = NAME_NUM;
			
			break;
			
      case GENDER_NUM :
    	  while(true) {
				System.out.print("성별(남자 or 여자) 입력 >>");
				gender = scan.nextLine();
				
				if(gender.equals("남자") || gender.equals("여자")) {
					break;
				}else {
					System.out.println("다시 입력해주세요.");
				}
			}
			searchData = gender;
			number = GENDER_NUM;
			break;

      case EXIT :
			System.out.println("검색 취소 완료");
			flag = true;
			break;
		}
		if(flag) {			
			return; 
		} 	
			list = DBController.StudentSearch(searchData, number);
			
			if(list.size() <= 0) {
				System.out.println(searchData +"찾지 못했습니다.");
				return;
			}
		for(StudentDataSub data : list) {
			System.out.println(data);
		}	
			
			
      }
   
   private static int studentMenu() {
		boolean flag =false;
		int no = 0;
		
		while(!flag) {
			System.out.println("*******************");
			System.out.println("1. 이름 2. 성별 3. 종료");
			System.out.println("*******************");
			System.out.print("번호선택 >>");
			
			try {
				no = Integer.parseInt(scan.nextLine());
			}catch(InputMismatchException e) {
				System.out.println("다시 입력해주세요!");
				continue;
			}catch(Exception e) {
				System.out.println("다시 입력해주세요!");
				continue;
			}
			
			if(no >= 1 && no <= 3) {
				flag = true;
			}else {
				System.out.println("다시 입력해주세요!(1~3)");
				continue;
			}
			
		}//end of while
		return no;
	}
   
   //삭제 (이름)
   private static void deleteStudent() {
	  final int NAME_NUM = 1;
	  String name = null;
	  String deleteData = null;
	  int number = 0;
	  int resultNumber = 0;
	  
	  while(true) {
		  System.out.print("찾고자 하는 이름 입력>>");
			name = scan.nextLine();

			if(patternCheck(name, NAME)) {
				break;
			}else {
				System.out.println("다시 입력해주세요.");
			}
	  }
	  deleteData = name;
	  //확장성을 위해서
	  number = NAME_NUM;
	  
	  resultNumber = DBController.StudentDelete(deleteData, number);
		
		 if(resultNumber != 0) {
			 System.out.println(name+" 이름 레코드가 삭제완료했습니다.");
		 }else {
			 System.out.println(name+" 삭제 실패하였습니다.");
		 }
      
   }
   
   //수정(이름) -> 내용수정(국어, 수학, 영어 총점, 평균, 등급)
   private static void updateStudent() {
    
	   final int NAME_NUM = 1;
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   //검색할 내용을 선택요청(전화번호, 이름, 성별)
	   String name = null;
	   String searchData = null;
	   int number = 0;
	   int resultNumber = 0;
	   int kor = 0;
	   int math = 0;
	   int eng = 0;
	   
	   int total = 0;
	   double avr = 0.0;
	   String grade = null;
	   
	   //수정할 이름 검색
	   while(true) {
		   System.out.print("수정하고자 하는 이름 입력 >>");
		   name = scan.nextLine();
		   
		   if(patternCheck(name, NAME)) {
			   break;
		   }else {
			   System.out.println("다시 입력해주세요.");
		   }
	   }
	   searchData = name;
	   number = NAME_NUM;
	   
	   list = DBController.StudentSearch(searchData, number);
	   
	   if(list.size() <= 0) {
			System.out.println(searchData +"찾지 못했습니다.");
			return;
		}
	   
	   StudentDataSub data_buffer = null;
	   for(StudentDataSub data : list) {
		   System.out.println(data);
		   data_buffer = data;
	   }
	   
	   //2. 이름이 있으면 수정할 작업을 받아서 수정요청
	   while(true) {
		   System.out.print("["+data_buffer.getKor()+"] \n수정할 국어 점수 입력 >>");
		   kor = scan.nextInt();
		   
		   if(kor >= 0 && kor <= 100) {
			   break;
		   }else {
			   System.out.println("다시 입력해주세요.");
		   }
	   }
		   
	   while(true) {
		   System.out.print("["+data_buffer.getMath()+"] \n수정할 수학 점수 입력 >>");
		   math = scan.nextInt();
		   
		   if(math >= 0 && math <= 100) {
			   break;
		   }else {
			   System.out.println("다시 입력해주세요.");
		   }
	   }
		   
	   while(true) {
		   System.out.print("["+data_buffer.getEng()+"] \n수정할 영어 점수 입력 >>");
		   eng = scan.nextInt();
		   
		   if(eng >= 0 && eng <= 100) {
			   break;
		   }else {
			   System.out.println("다시 입력해주세요.");
		   }
	   }
	   scan.nextLine();
	   
	   StudentDataSub studentDataSub = new StudentDataSub();
	   //입력한 kor, math, eng을 studentDataSub에 값 넣기
	   studentDataSub.setKor(kor);
	   studentDataSub.setMath(math);
	   studentDataSub.setEng(eng);
	   //총점, 평균, 성적 함수 가져오기
	   studentDataSub.totalScore();
	   studentDataSub.avrScore();
	   studentDataSub.gradeScore();
	   
	   total = studentDataSub.getTotal();
	   avr = studentDataSub.getAvr();
	   grade =studentDataSub.getGrade();
	   
		//3. 결과값 확인
		resultNumber = DBController.StudentUpdate(name, kor, math, eng, total, avr, grade);

		if(resultNumber != 0) {
			System.out.println(name + "점수 수정완료했습니다.");
		}else {
			System.out.println(name + "점수 수정실패하였습니다.");
		}
		
		return;
   }
   //출력
   private static void printStudent() {
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      
      list = DBController.StudentDataSelect();
      
      if(list.size() <= 0) {
         System.out.println("출력할 전화번호부 내용이 없어요");
         return;
      }
      
      for(StudentDataSub data: list) {
         System.out.println(data.toString());
      }
      
   }
   
   //정렬 = 정렬은 되도록 자바보다 DB로 하는것이 빠르다. (더 특화 되어있기 때문이고 결과값만 받으면 되기 때문에)
   private static void sortStudent() {
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   int no = 0;
	   boolean flag = false;
	   while(!flag) {
		   
		//1. 정렬방식(오름차순, 내림차순)
		System.out.println("[등급] 1. 오름차순, 2. 내림차순");
		System.out.print("정렬방식 선택>>");
		
		try {
			no = Integer.parseInt(scan.nextLine());
		}catch(InputMismatchException e) {
			System.out.println("Warning : Input Number plz!");
			continue;
		}catch(Exception e) {
			System.out.println("Warning : Input Number plz!");
			continue;
		}
		   
		if(no >= 1 && no <= 2) {
			flag = true;
		}else {
			System.out.println("다시 입력해주세요!(1~2)");
			continue;
		}
   }//end of while
	   
	   //2. 출력문 가져오기
	   list = DBController.studentSort(no);
	   
	   if(list.size() <= 0) {
			System.out.println("nothing to sort");
			return;
		}
	
		for(StudentDataSub data: list) {
			System.out.println(data.toString());
		}
		
		return;
	}
   
   	  //패턴매치 처리함수
      private static boolean patternCheck(String patternData, int patternType) {
         String filter = null;
         
         switch(patternType) {
         case NAME : filter = "^[가-힣]{2,5}$"; break;
         case BIRTHDAY : filter ="^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$"; break;
         }
         
         Pattern pattern = Pattern.compile(filter);
           Matcher matcher = pattern.matcher(patternData);
         return matcher.matches();
      }


   private static int selectMenu() {
      boolean flag =false;
      int no = 0;
      
      while(!flag) {
    	  
         System.out.println("==================================================");
         System.out.println("1. 입력 2. 조회 3. 삭제 4. 수정 5. 출력 6. 정렬 7. 종료");
         System.out.println("==================================================");
         System.out.print("번호선택 >>");
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e) {
            System.out.println("다시 입력해주세요!");
            continue;
         }catch(Exception e) {
            System.out.println("다시 입력해주세요!");
            continue;
         }
         
         if(no >= 1 && no <= 7) {
            flag = true;
         }else {
            System.out.println("다시 입력해주세요!(1~7)");
            continue;
         }
         
      }//end of while
      
      return no;
   }

}
